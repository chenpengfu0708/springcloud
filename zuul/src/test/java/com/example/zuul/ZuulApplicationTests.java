package com.example.zuul;

import com.example.commons.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ZuulApplicationTests {

    @Autowired
    private RedisUtils redisUtils;

    @Test
    void contextLoads() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "1");
        map.put("key2", "12");
        map.put("key3", "123");
        map.put("key4", "1234");
        redisUtils.setMap("mapKey", map, 2, TimeUnit.MINUTES);
    }

}
