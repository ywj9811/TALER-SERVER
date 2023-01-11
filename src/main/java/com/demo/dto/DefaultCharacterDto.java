package com.demo.dto;

import com.demo.domain.Usercharacter;

public class DefaultCharacterDto {
    public static Usercharacter dtoToEntity(Usercharacter usercharacter, Long bookId) {
        return Usercharacter.builder()
                .userId(usercharacter.getUserId())
                .bookId(bookId)
                .gender(usercharacter.getGender())
                .nickname(usercharacter.getNickname())
                .headColor(usercharacter.getHeadColor())
                .headStyle(usercharacter.getHeadStyle())
                .topColor(usercharacter.getTopColor())
                .topStyle(usercharacter.getTopStyle())
                .pantsColor(usercharacter.getPantsColor())
                .pantsStyle(usercharacter.getPantsStyle())
                .shoesColor(usercharacter.getShoesColor())
                .shoesStyle(usercharacter.getShoesStyle())
                .faceColor(usercharacter.getFaceColor())
                .faceStyle(usercharacter.getFaceStyle())
                .build();
    }
}
