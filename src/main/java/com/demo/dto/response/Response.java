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

    //오류 return시 result를 제외하기 위한 생성자
    public Response(String message, int code) {
        this.message = message;
        this.code = code;
    }
}
