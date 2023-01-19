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

    private String wordVoiceUrl;

    private String wordText;

    private int priority;

    public void updateMind(String wordText, String wordPictureUrl, String wordVoiceUrl) {
        this.wordPictureUrl = wordPictureUrl;
        this.wordText = wordText;
        this.wordVoiceUrl = wordVoiceUrl;
    }
}
