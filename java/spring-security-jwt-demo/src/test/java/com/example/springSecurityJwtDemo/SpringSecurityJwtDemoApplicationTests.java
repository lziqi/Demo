package com.example.springSecurityJwtDemo;

import com.example.springSecurityJwtDemo.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootTest
class SpringSecurityJwtDemoApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * 测试加密
     */
    @Test
    void contextLoads() {
        System.out.println(passwordEncoder.encode("123456"));
        System.out.println(passwordEncoder.matches("123456",
                "$2a$10$cXF0QH18Ng9R4yhugEV7oODtj0OJKGe4.DCypXk0FCjH5TZf9txRe"));
    }

    @Test
    void permissionMapper(){

    }

}
