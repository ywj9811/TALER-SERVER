package com.demo.dto;

import com.demo.domain.Usercharacter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsercharacterDto {
    private Long userId;

    private Long bookId;

    private String gender;

    private String nickname;

    private String headStyle;

    private String headColor;

    private String topStyle;

    private String topColor;

    private String pantsStyle;

    private String pantsColor;

    private String shoesStyle;

    private String shoesColor;

    private String faceColor;

    private String faceStyle;

    public UsercharacterDto(Long userId, Long bookId, String gender, String nickname, String headStyle, String headColor, String topStyle, String topColor, String pantsStyle, String pantsColor, String shoesStyle, String shoesColor, String faceColor, String faceStyle) {
        this.userId = userId;
        this.bookId = bookId;
        this.gender = gender;
        this.nickname = nickname;
        this.headStyle = headStyle;
        this.headColor = headColor;
        this.topStyle = topStyle;
        this.topColor = topColor;
        this.pantsStyle = pantsStyle;
        this.pantsColor = pantsColor;
        this.shoesStyle = shoesStyle;
        this.shoesColor = shoesColor;
        this.faceColor = faceColor;
        this.faceStyle = faceStyle;
    }

    public Usercharacter insertDtoToUsercharacter(UsercharacterDto usercharacterDto){
        return Usercharacter.builder()
                .userId(usercharacterDto.getUserId())
                .bookId(usercharacterDto.getBookId())
                .gender(usercharacterDto.getGender())
                .nickname(usercharacterDto.getNickname())
                .headStyle(usercharacterDto.getHeadStyle())
                .headColor(usercharacterDto.getHeadColor())
                .topStyle(usercharacterDto.getTopStyle())
                .topColor(usercharacterDto.getTopColor())
                .pantsColor(usercharacterDto.getPantsColor())
                .pantsStyle(usercharacterDto.getPantsStyle())
                .shoesColor(usercharacterDto.getShoesColor())
                .shoesStyle(usercharacterDto.getShoesStyle())
                .faceColor(usercharacterDto.getFaceColor())
                .faceStyle(usercharacterDto.getFaceStyle())
                .build();
    }
}
