package com.example.springSecurityJwtRsa.auth.filter;


import com.example.springSecurityJwtRsa.util.utils.JwtUtils;
import com.example.springSecurityJwtRsa.auth.utils.RsaKeyProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private RsaKeyProperties rsaKeyProperties;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,RsaKeyProperties rsaKeyProperties) {
        super(authenticationManager);
        this.rsaKeyProperties = rsaKeyProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader("token");
        /*如果请求头中有token，则进行解析，并且设置认证信息*/
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        super.doFilterInternal(request, response, chain);
    }

    /*这里从token头中获取用户信息并新建一个token*/
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String username = JwtUtils.getUsername(tokenHeader, rsaKeyProperties.getPublicKey());
        if (username != null) {
            List<SimpleGrantedAuthority> userRoles = JwtUtils.getUserRole(tokenHeader,rsaKeyProperties.getPublicKey());
            return new UsernamePasswordAuthenticationToken(username, null, userRoles);
        }
        return null;
    }
}
