package com.demo.dto;

import com.demo.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserInsertDto {
    private String nickname;
    private int age;
    private String profileColor;
    private int status;
    private String pw;
    private String phonenumber;

    public User dtoToUser(UserInsertDto userInsertDto){
        return User.builder()
                .nickname(userInsertDto.getNickname())
                .age(userInsertDto.getAge())
                .profileColor(userInsertDto.getProfileColor())
                .status(userInsertDto.getStatus())
                .pw(userInsertDto.getPw())
                .phonenumber(userInsertDto.getPhonenumber())
                .build();
    }

}
