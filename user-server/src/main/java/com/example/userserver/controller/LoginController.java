package com.example.userserver.controller;

import com.example.commons.constants.MyConstants;
import com.example.commons.dto.request.LoginRequestDto;
import com.example.commons.dto.response.CommonsResponse;
import com.example.commons.entity.User;
import com.example.commons.utils.RedisUtils;
import com.example.userserver.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class LoginController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisUtils redisUtils;


    @PostMapping(value = "/login")
    public CommonsResponse login(@RequestBody LoginRequestDto request) {
        User user = userMapper.getByUser(request.getName(), request.getEmail());
        if (user != null) {
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            redisUtils.setKey(MyConstants.USER_TOKEN_KEY + token, token, 1L, TimeUnit.MINUTES);
            return new CommonsResponse().code(0).msg("登录成功").data(token);
        }
        return new CommonsResponse().code(-1).msg("登录失败");
    }


}
