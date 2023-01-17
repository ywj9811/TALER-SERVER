package com.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {
    private Object result;
    private String message;
    private int code;

    public Response() {}
}
