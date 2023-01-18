package com.demo.domain;

import lombok.Getter;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Immutable
//view이기 때문에 @Immutable을 통해 수정이 불가능하도록 만들었음
public class Roomview {
    @Id
    private Long bookroomId;
    private Long userId;
    private Long bookId;
    private Long characterId;
    private String themeColor;
    private String themeMusicUrl;
    private String bookTitle;
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
