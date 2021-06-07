package com.example.springSecurityJwtRsa.gateway;

import com.example.springSecurityJwtRsa.gateway.utils.RsaKeyProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayApplicationTests {

    @Autowired
    private RsaKeyProperties rsaKeyProperties;

    @Test
    public void contextLoads() {
        System.out.println(rsaKeyProperties);
    }

}
