package com.demo.dto.response;

import lombok.Data;

@Data
public class Response {
    private Object result;
    private String message;
    private int code;
}
