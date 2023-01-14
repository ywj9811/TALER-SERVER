package com.demo.dto.response;

import com.demo.domain.Roomview;
import lombok.Data;

@Data
public class GetBookroomResponse {
    private Roomview roomview;
    private String message;
    private int code;
}
