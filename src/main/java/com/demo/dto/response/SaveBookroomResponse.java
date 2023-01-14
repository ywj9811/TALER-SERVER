package com.demo.dto.response;

import com.demo.domain.Bookroom;
import lombok.Data;

@Data
public class SaveBookroomResponse {
    private Bookroom bookroom;
    private String message;
    private int code;

}
