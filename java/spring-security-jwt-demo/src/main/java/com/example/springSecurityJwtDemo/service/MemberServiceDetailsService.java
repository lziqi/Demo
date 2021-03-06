package com.example.springSecurityJwtDemo.service;

import com.example.springSecurityJwtDemo.entity.Permission;
import com.example.springSecurityJwtDemo.entity.User;
import com.example.springSecurityJwtDemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Slf4j
public class MemberServiceDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1.根据该用户名称查询在数据库中是否存在
        User user = userService.findByUsername(username);
        if (user == null) {
            return null;
        }
        // 2.查询对应的用户权限
        List<Permission> listPermission = userService.findPermissionByUsername(username);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        listPermission.forEach(_user -> {
            authorities.add(new SimpleGrantedAuthority(_user.getPermTag()));
        });
        log.info(">>> authorities:{} <<<", authorities);
        // 3.将该权限添加到security
        user.setAuthorities(authorities);
        return user;
    }
}
