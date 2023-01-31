package com.demo.dto;

import com.demo.domain.Usercharacter;

public class DefaultCharacterDto {
    public static Usercharacter dtoToEntity(Usercharacter usercharacter, Long bookId) {
        return Usercharacter.builder()
                .userId(usercharacter.getUserId())
                .bookId(bookId)
                .gender(usercharacter.getGender())
                .nickname(usercharacter.getNickname())
                .headStyle(usercharacter.getHeadStyle())
                .topStyle(usercharacter.getTopStyle())
                .pantsStyle(usercharacter.getPantsStyle())
                .shoesStyle(usercharacter.getShoesStyle())
                .faceStyle(usercharacter.getFaceStyle())
                .build();
    }
}
