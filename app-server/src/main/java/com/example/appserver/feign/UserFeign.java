package com.example.appserver.feign;

import com.example.appserver.config.FeignConfig;
import com.example.commons.dto.UserDto;
import com.example.commons.dto.request.LoginRequestDto;
import com.example.commons.dto.response.CommonsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "user-server", configuration = FeignConfig.class)
public interface UserFeign {

    @GetMapping("/getUser")
    String getUser();

    @GetMapping(value = "/getUserByName")
    String getUserByName(@RequestParam("name") String name);

    @PostMapping(value = "/getUserByPost")
    String getUserByPost(@RequestBody UserDto userDto);

    @PostMapping(value = "/getUserEntity")
    UserDto getUserEntity(@RequestBody UserDto userDto);

    @PostMapping(value = "/login")
    CommonsResponse login(@RequestBody LoginRequestDto request);

}
