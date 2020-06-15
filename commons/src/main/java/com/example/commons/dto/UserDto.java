package com.example.commons.dto;

import lombok.Data;

@Data
public class UserDto {

    private String name;

    private String sex;

    public UserDto() {
        this.name = "123";
        this.sex = "123";
    }
}
