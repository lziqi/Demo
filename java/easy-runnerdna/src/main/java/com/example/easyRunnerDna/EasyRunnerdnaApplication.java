package com.example.easyRunnerDna;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class EasyRunnerdnaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyRunnerdnaApplication.class, args);
    }

}
