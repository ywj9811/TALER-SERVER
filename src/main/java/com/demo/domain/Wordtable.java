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
public class Wordtable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordId;

    private Long bookroomId;

    private Long mainId;

    private String wordPictureUrl;

    private int wordMain;

    private String wordVoiceUrl;

    private String wordText;

    public void updateWord(String wordPictureUrl, String wordVoiceUrl, String wordText) {
        this.wordPictureUrl = wordPictureUrl;
        this.wordText = wordText;
        this.wordVoiceUrl = wordVoiceUrl;
    }
}
