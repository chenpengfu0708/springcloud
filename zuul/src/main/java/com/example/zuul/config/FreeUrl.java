package com.example.zuul.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author fu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "freeurl")
public class FreeUrl {

    private List<String> list;

}
