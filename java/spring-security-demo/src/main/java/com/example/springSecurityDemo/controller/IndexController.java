package com.example.springSecurityDemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName IndexController
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
@RestController
@RequestMapping("/")
public class IndexController {
    /**
     * 跳转到首页
     *
     * @return
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/addMember")
    public String addMember() {
        return "新增用户";
    }

    @GetMapping("/delMember")
    public String delMember() {
        return "删除用户";
    }

    @GetMapping("/updateMember")
    public String updateMember() {
        return "修改用户";
    }

    @GetMapping("/showMember")
    public String showMember() {
        return "查询用户";
    }


}
