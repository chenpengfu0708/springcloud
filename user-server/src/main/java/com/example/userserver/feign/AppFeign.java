package com.example.userserver.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@FeignClient(name = "app-server")
public interface AppFeign {

    @GetMapping("/app/getApp")
    String getApp();

}
