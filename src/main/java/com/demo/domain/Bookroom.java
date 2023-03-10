package com.demo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookroomId;

    private Long userId;

    private Long bookId;

    private String themeColor;

    public void updateThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    private String themeMusicUrl;

    public void updateThemeMusicUrl(String themeMusicUrl) {
        this.themeMusicUrl = themeMusicUrl;
    }
}
