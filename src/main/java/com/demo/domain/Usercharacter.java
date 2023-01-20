package com.demo.domain;

import com.demo.dto.EditCharacterDto;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Usercharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public void editCharacter(EditCharacterDto editCharacterDto) {
        this.gender = editCharacterDto.getGender();
        this.nickname = editCharacterDto.getNickname();
        this.headStyle = editCharacterDto.getHeadStyle();
        this.headColor = editCharacterDto.getHeadColor();
        this.topStyle = editCharacterDto.getTopStyle();
        this.topColor = editCharacterDto.getTopColor();
        this.pantsStyle = editCharacterDto.getPantsStyle();
        this.pantsColor = editCharacterDto.getPantsColor();
        this.shoesStyle = editCharacterDto.getShoesStyle();
        this.shoesColor = editCharacterDto.getShoesColor();
        this.faceColor = editCharacterDto.getFaceColor();
        this.faceStyle = editCharacterDto.getFaceStyle();
    }
}
