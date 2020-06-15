package com.example.democonsume.controller;

import com.example.demobase.entity.User;
import com.example.demoservice.service.ConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumeController {

    @Autowired
    private ConsumeService consumeService;

    @GetMapping(value = "/check")
    public Object check(@RequestParam String token) {
        User user = consumeService.check(token);
        if (user != null) {
            return user;
        } else {
            return "没有该用户登录信息";
        }
    }

}
