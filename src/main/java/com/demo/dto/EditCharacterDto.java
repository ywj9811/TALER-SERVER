package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditCharacterDto {
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
}
