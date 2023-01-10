package com.demo.domain;

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
}
