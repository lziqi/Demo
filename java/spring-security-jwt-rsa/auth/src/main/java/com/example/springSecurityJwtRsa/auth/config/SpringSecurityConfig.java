package com.example.springSecurityJwtRsa.auth.config;

import com.example.springSecurityJwtRsa.auth.filter.JWTAuthorizationFilter;
import com.example.springSecurityJwtRsa.auth.filter.JWTLoginFilter;
import com.example.springSecurityJwtRsa.auth.service.MemberServiceDetailsService;
import com.example.springSecurityJwtRsa.auth.utils.RsaKeyProperties;
import com.example.springSecurityJwtRsa.util.mapper.*;
import com.example.springSecurityJwtRsa.util.entity.PermissionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberServiceDetailsService memberServiceDetailsService;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RsaKeyProperties rsaKeyProperties;


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
                .addFilter(new JWTAuthorizationFilter(authenticationManager(),rsaKeyProperties))
                .addFilter(new JWTLoginFilter(authenticationManager(),rsaKeyProperties)).csrf().disable()
                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}
