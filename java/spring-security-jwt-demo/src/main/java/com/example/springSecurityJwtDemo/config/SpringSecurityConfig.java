package com.example.springSecurityJwtDemo.config;

import com.example.springSecurityJwtDemo.entity.PermissionEntity;
import com.example.springSecurityJwtDemo.filter.JWTAuthorizationFilter;
import com.example.springSecurityJwtDemo.filter.JWTLoginFilter;
import com.example.springSecurityJwtDemo.mapper.PermissionMapper;
import com.example.springSecurityJwtDemo.service.MemberServiceDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsUtils;

import java.util.List;

@CrossOrigin
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

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
    protected void configure(HttpSecurity http) throws Exception {
        List<PermissionEntity> allPermission = permissionMapper.findAllPermission();

        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = http.authorizeRequests();

        //对所有的权限注册
        allPermission.forEach((permission) -> {
            expressionInterceptUrlRegistry.antMatchers(permission.getUrl()).
                    hasAnyAuthority(permission.getPermTag());

        });

        //放行login，其余均走权限认证
        expressionInterceptUrlRegistry.antMatchers("/auth/login").permitAll()
                .antMatchers("/**").fullyAuthenticated()
                .and().formLogin().and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                .addFilter(new JWTLoginFilter(authenticationManager())).csrf().disable()
                .addFilterBefore(new WebSecurityCorsFilter(), ChannelProcessingFilter.class)
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //跨域
    }
}
