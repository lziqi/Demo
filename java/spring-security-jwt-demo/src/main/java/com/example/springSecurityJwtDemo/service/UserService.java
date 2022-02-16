package com.example.springSecurityJwtDemo.service;

import com.example.springSecurityJwtDemo.entity.Permission;
import com.example.springSecurityJwtDemo.entity.User;
import com.example.springSecurityJwtDemo.mapper.PermissionMapper;
import com.example.springSecurityJwtDemo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    public User findByUsername(String username){
        return userMapper.findByUsername(username);
    }

    public List<Permission> findPermissionByUsername(String username){
        String userID = userMapper.findIDByUsername(username);
        return permissionMapper.findPermissionByRoleID(userMapper.findRoleIDByUserID(userID));
    }
}
