package com.example.springSecurityJwtDemo.mapper;

import com.example.springSecurityJwtDemo.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
public interface PermissionMapper {
    @Select(" select * from sys_permission ")
    List<PermissionEntity> findAllPermission();
}
