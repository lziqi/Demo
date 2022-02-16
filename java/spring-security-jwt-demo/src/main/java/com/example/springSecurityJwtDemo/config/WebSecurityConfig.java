package com.example.springSecurityJwtDemo.config;

import com.example.springSecurityJwtDemo.entity.Permission;
import com.example.springSecurityJwtDemo.filter.JWTAuthorizationFilter;
import com.example.springSecurityJwtDemo.filter.JWTLoginFilter;
import com.example.springSecurityJwtDemo.mapper.PermissionMapper;
import com.example.springSecurityJwtDemo.service.MemberServiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@CrossOrigin
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberServiceDetailsService memberServiceDetailsService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用BCrypt进行密码加密
        auth.userDetailsService(memberServiceDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring().antMatchers("/fail.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //获取所有权限
        List<Permission> permissions = permissionMapper.list();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.authorizeRequests();

        //对所有的权限注册,对应的url需要对应的权限才能放行
        permissions.forEach((permission) -> {
            expressionInterceptUrlRegistry.antMatchers(permission.getUrl()).hasAnyAuthority(permission.getPermTag());
        });

        http.formLogin()
                .loginPage("/fail.html");

        //放行auth login，对其他进行限制，添加login与auth过滤器，添加webcors跨域过滤器
        expressionInterceptUrlRegistry.antMatchers("/auth/login").permitAll()
                .antMatchers("/**").fullyAuthenticated()
                .and().formLogin().and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .addFilter(new JWTLoginFilter(authenticationManager())).csrf().disable()
                //跨域
                .addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class)
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
