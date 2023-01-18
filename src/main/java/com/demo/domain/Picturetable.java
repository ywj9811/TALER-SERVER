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
public class Picturetable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pictureId;

    private Long bookroomId;

    private String pictureUrl;

    public void updatePictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
