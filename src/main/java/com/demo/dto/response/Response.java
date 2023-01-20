package com.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private Object result;
    private String message;
    private int code;

    public Response(String message, int code) {
        this.code = code;
        this.message = message;
    }
}
