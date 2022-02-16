package com.example.springSecurityJwtDemo.filter;

import com.example.springSecurityJwtDemo.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@CrossOrigin
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * 从http请求头中获取token信息
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        if (request.getHeader("token") != null) {
            String tokenHeader = request.getHeader("token");
            SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
        }
        super.doFilterInternal(request, response, chain);
    }

    /**
     * 获取token对应的用户，获取用户对应的权限 add权限
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        String username = JwtUtils.getUsername(tokenHeader);
        if (username != null) {
            List<SimpleGrantedAuthority> userRoles = JwtUtils.getUserRole(tokenHeader);
            return new UsernamePasswordAuthenticationToken(username, null, userRoles);
        }
        return null;
    }
}
