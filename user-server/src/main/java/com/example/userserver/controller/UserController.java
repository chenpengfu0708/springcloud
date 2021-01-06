package com.example.userserver.controller;

import com.example.commons.dto.UserDto;
import com.example.userserver.feign.AppFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping(value = "/getApp")
    public String getApp() {
        return appFeign.getApp();
    }

}
