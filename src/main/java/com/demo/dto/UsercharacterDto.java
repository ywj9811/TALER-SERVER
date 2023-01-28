package com.demo.dto;

import com.demo.domain.Usercharacter;
import lombok.Data;

@Data
public class UsercharacterDto {
    private Long userId;

    private Long bookId;

    private String gender;

    private String nickname;

    private String headStyle;

    private String topStyle;

    private String pantsStyle;

    private String shoesStyle;

    private String faceStyle;

    public UsercharacterDto(String gender, String nickname, String headStyle, String topStyle, String pantsStyle, String shoesStyle, String faceStyle) {
        this.gender = gender;
        this.nickname = nickname;
        this.headStyle = headStyle;
        this.topStyle = topStyle;
        this.pantsStyle = pantsStyle;
        this.shoesStyle = shoesStyle;
        this.faceStyle = faceStyle;
    }

    public Usercharacter insertDtoToUsercharacter(UsercharacterDto usercharacterDto){
        return Usercharacter.builder()
                .userId(usercharacterDto.getUserId())
                .bookId(usercharacterDto.getBookId())
                .gender(usercharacterDto.getGender())
                .nickname(usercharacterDto.getNickname())
                .headStyle(usercharacterDto.getHeadStyle())
                .topStyle(usercharacterDto.getTopStyle())
                .pantsStyle(usercharacterDto.getPantsStyle())
                .shoesStyle(usercharacterDto.getShoesStyle())
                .faceStyle(usercharacterDto.getFaceStyle())
                .build();
    }
}
