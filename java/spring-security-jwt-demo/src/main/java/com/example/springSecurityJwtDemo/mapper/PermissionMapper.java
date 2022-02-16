package com.example.springSecurityJwtDemo.mapper;

import com.example.springSecurityJwtDemo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
public interface PermissionMapper {
    List<Permission> list();

    List<Permission> findPermissionByRoleID(String roleID);
}


