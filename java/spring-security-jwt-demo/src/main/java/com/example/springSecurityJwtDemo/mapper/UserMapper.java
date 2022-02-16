package com.example.springSecurityJwtDemo.mapper;


import com.example.springSecurityJwtDemo.entity.Permission;
import com.example.springSecurityJwtDemo.entity.Role;
import com.example.springSecurityJwtDemo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名称查询用户
     *
     * @param username
     * @return
     */
    User findByUsername(String username);

    String findIDByUsername(String username);

    String findRoleIDByUserID(String userID);

}