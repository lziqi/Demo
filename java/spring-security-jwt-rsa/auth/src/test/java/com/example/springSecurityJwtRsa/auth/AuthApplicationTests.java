package com.example.springSecurityJwtRsa.auth;

import com.example.springSecurityJwtRsa.auth.utils.RsaKeyProperties;
import com.example.springSecurityJwtRsa.util.utils.RsaUtils;
import com.example.springSecurityJwtRsa.util.mapper.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthApplicationTests {
    @Autowired
    RsaKeyProperties rsaKeyProperties;

    @Autowired
    PermissionMapper permissionMapper;

    /*生成rsa key*/
    @Test
    public void contextLoads() {
        try {
            RsaUtils.generateKey("/home/rsa/pub_key","/home/rsa/pri_key","123456",2048);
            System.out.println(rsaKeyProperties.getPrivateKey());
            System.out.println(permissionMapper.findAllPermission());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
