package com.example.springSecurityJwtRsa.auth.filter;

import com.example.springSecurityJwtRsa.util.entity.UserEntity;
import com.example.springSecurityJwtRsa.util.utils.JwtUtils;
import com.example.springSecurityJwtRsa.auth.utils.RsaKeyProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private RsaKeyProperties rsaKeyProperties;

    public JWTLoginFilter(AuthenticationManager authenticationManager,RsaKeyProperties rsaKeyProperties) {
        this.authenticationManager = authenticationManager;
        this.rsaKeyProperties = rsaKeyProperties;
        /*过滤器处理路径*/
        super.setFilterProcessesUrl("/auth/login");
    }

    /*接收并解析用户凭证*/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            /*从application/json中解析出user*/
            UserEntity user = new ObjectMapper().readValue(request.getInputStream(), UserEntity.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*用户成功登录后，调用方法，在这个方法里生成token*/
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        UserEntity userEntity = (UserEntity) auth.getPrincipal();
        String token = JwtUtils.generateJsonWebToken(userEntity,rsaKeyProperties.getPrivateKey());
        response.addHeader("token", token);
    }

    /*登录失败，调用方法*/
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }

}
