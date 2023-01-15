package com.demo.dto;

import com.demo.domain.Parent;
import lombok.Data;

@Data
public class ParentInsertDto {
    private Long userId;
    private String nickname;
    private int age;
    private int status;
    private String pw;

    public Parent dtoToParent(ParentInsertDto parentInsertDto){
        return Parent.builder()
                .userId(parentInsertDto.getUserId())
                .nickname(parentInsertDto.getNickname())
                .age(parentInsertDto.getAge())
                .status(parentInsertDto.getStatus())
                .pw(parentInsertDto.getPw())
                .build();
    }
}
