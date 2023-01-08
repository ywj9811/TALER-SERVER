package com.demo.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Mindmap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mindId;

    private Long bookroomId;

    private String wordPictureUrl;

//    private int wordMain;
    /**
     * 용도를 다시 생각해보자
     */

    private String wordVoiceUrl;

    private String wordText;

    private int priority;
}
