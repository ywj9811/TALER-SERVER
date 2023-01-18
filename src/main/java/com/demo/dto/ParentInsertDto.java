package com.demo.dto;

import com.demo.domain.Parent;
import lombok.Data;

@Data
public class ParentInsertDto {
    private String nickname;
    private int age;
    private String pw;
    private String userNickname;
    private String userPw;

    public Parent dtoToParent(ParentInsertDto parentInsertDto, Long userId ){
        return Parent.builder()
                .userId(userId)
                .nickname(parentInsertDto.getNickname())
                .age(parentInsertDto.getAge())
                .status(1)
                .pw(parentInsertDto.getPw())
                .authority("parent")
                .build();
    }
}
