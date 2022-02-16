package com.example.springSecurityJwtDemo.controller;

import com.example.springSecurityJwtDemo.mapper.PermissionMapper;
import com.example.springSecurityJwtDemo.mapper.UserMapper;
import com.example.springSecurityJwtDemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName IndexController
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
@CrossOrigin
@RestController
@RequestMapping("/")
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/hello")
    public String hello(){
        System.out.println(userService.findByUsername("admin"));
        System.out.println(userService.findPermissionByUsername("admin"));
        return "hello";
    }

    @GetMapping("/add")
    public String add() {
        return "新增用户";
    }

    @GetMapping("/del")
    public String del() {
        return "删除用户";
    }

    @GetMapping("/update")
    public String update() {
        return "修改用户";
    }

    @GetMapping("/show")
    public String show() {
        return "查询用户";
    }


}
