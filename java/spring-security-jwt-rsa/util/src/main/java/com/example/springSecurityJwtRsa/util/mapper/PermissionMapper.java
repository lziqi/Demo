package com.example.springSecurityJwtRsa.util.mapper;

import com.example.springSecurityJwtRsa.util.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
public interface PermissionMapper {
    @Select(" select * from sys_permission ")
    List<PermissionEntity> findAllPermission();
}
