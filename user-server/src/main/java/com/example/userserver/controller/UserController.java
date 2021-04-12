package com.example.userserver.controller;

import com.example.commons.dto.UserDto;
import com.example.userserver.feign.AppFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class UserController {

    @Autowired
    private AppFeign appFeign;


    @GetMapping(value = "/getUser")
    public String getUser(HttpServletRequest request) {
        System.out.println("token = " + request.getHeader("Authorization"));
        return "这是user服务";
    }

    @GetMapping(value = "/getUserByName")
    public String getUserByName(@RequestParam("name") String name) {
        return "这是user服务:name = " + name;
    }

    @PostMapping(value = "/getUserByPost")
    public String getUserByPost(@RequestBody UserDto userDto) {
        return "这是user服务，userDto = " + userDto;
    }

    @PostMapping(value = "/getUserEntity")
    public UserDto getUserEntity(@RequestBody UserDto userDto) {
        return userDto;
    }

    @HystrixCommand(fallbackMethod = "feignCallBack")
    @GetMapping(value = "/getApp")
    public String getApp() {
        log.info("===这是==getApp接口");
        return appFeign.getApp();
    }

    public String feignCallBack() {
        System.out.println("feign调用断路啦。。。。。");
        return "feignCallBack:";
    }
}
