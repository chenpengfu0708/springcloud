package com.example.appserver.controller;

import com.example.appserver.feign.UserFeign;
import com.example.commons.dto.UserDto;
import com.example.commons.dto.request.LoginRequestDto;
import com.example.commons.dto.response.CommonsResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/app")
public class AppController {

    @Resource
    private UserFeign userFeign;

    @GetMapping(value = "/getApp")
    public String getApp() {
        UserDto userDto = new UserDto();
        System.out.println(userDto);
        return "这是app服务";
    }


    @GetMapping(value = "/getUser")
    public String getUser(HttpServletRequest request) {
        System.out.println(userFeign.getUser());
        System.out.println(userFeign.getUserByName("getUserByName"));
        System.out.println(userFeign.getUserByPost(new UserDto()));
        System.out.println(userFeign.getUserEntity(new UserDto()));
        return userFeign.getUser();
    }

    @PostMapping(value = "/appLogin")
    public CommonsResponse appLogin(@RequestBody LoginRequestDto request) {
        return userFeign.login(request);
    }

}
