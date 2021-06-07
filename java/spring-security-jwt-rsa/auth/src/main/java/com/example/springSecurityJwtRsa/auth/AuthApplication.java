package com.example.springSecurityJwtRsa.auth;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/*(exclude= {DataSourceAutoConfiguration.class})*/
@MapperScan("com.example.springSecurityJwtRsa.util.mapper")
@ComponentScan({"com.example.springSecurityJwtRsa"})
@SpringBootApplication
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}
