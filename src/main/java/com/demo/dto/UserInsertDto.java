package com.demo.dto;

import com.demo.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class UserInsertDto {
    @NotNull
    private String nickname;
    @NotNull
    private int age;
    @NotNull
    private String profileColor;
    @NotNull
    private String pw;
    @NotNull
    private String phonenumber;

    public User dtoToUser(UserInsertDto userInsertDto){
        return User.builder()
                .nickname(userInsertDto.getNickname())
                .age(userInsertDto.getAge())
                .profileColor(userInsertDto.getProfileColor())
                .status(1)
                .pw(userInsertDto.getPw())
                .phonenumber(userInsertDto.getPhonenumber())
                .authority("user")
                .build();
    }

}
