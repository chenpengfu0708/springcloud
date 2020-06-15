package com.example.commons.dto.response;

import lombok.Data;

@Data
public class CommonsResponse<T> {

    private Integer code;

    private String msg;

    private T data;

    public CommonsResponse(){}

    public CommonsResponse code(Integer code){
        this.code = code;
        return this;
    }

    public CommonsResponse msg(String msg) {
        this.msg = msg;
        return this;
    }

    public CommonsResponse data(T data) {
        this.data = data;
        return this;
    }
}
