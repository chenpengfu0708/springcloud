package com.example.commons.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Integer id;

    private String name;

    private Integer age;

    private String email;

    private Date createTime;
}
