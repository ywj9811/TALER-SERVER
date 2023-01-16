package com.demo.dto;

import com.demo.domain.Usercharacter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsercharacterUpdateDto {
    private Long characterId;

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

    public UsercharacterUpdateDto(Long characterId, Long userId, Long bookId, String gender, String nickname, String headStyle, String headColor, String topStyle, String topColor, String pantsStyle, String pantsColor, String shoesStyle, String shoesColor, String faceColor, String faceStyle) {
        this.characterId = characterId;
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

    public Usercharacter updateDtoToUsercharacter(UsercharacterUpdateDto usercharacterUpdateDto){
        return Usercharacter.builder()
                .characterId(usercharacterUpdateDto.getCharacterId())
                .userId(usercharacterUpdateDto.getUserId())
                .bookId(usercharacterUpdateDto.getBookId())
                .gender(usercharacterUpdateDto.getGender())
                .nickname(usercharacterUpdateDto.getNickname())
                .headStyle(usercharacterUpdateDto.getHeadStyle())
                .headColor(usercharacterUpdateDto.getHeadColor())
                .topStyle(usercharacterUpdateDto.getTopStyle())
                .topColor(usercharacterUpdateDto.getTopColor())
                .pantsColor(usercharacterUpdateDto.getPantsColor())
                .pantsStyle(usercharacterUpdateDto.getPantsStyle())
                .shoesColor(usercharacterUpdateDto.getShoesColor())
                .shoesStyle(usercharacterUpdateDto.getShoesStyle())
                .faceColor(usercharacterUpdateDto.getFaceColor())
                .faceStyle(usercharacterUpdateDto.getFaceStyle())
                .build();
    }
}
