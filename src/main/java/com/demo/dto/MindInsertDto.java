package com.demo.dto;

import com.demo.domain.Mindmap;
import lombok.Data;

@Data
public class MindInsertDto {
    Long bookroomId;

    String wordPictureUrl;

    int wordMain;

    String wordVoiceUrl;

    String wordText;

    int priority;

    public MindInsertDto(Long bookroomId, String wordPictureUrl, String wordVoiceUrl, String wordText, int priority) {
        this.bookroomId = bookroomId;
        this.wordPictureUrl = wordPictureUrl;
        this.wordVoiceUrl = wordVoiceUrl;
        this.wordText = wordText;
        this.priority = priority;
    }

    public Mindmap insertDtoToMindmap(MindInsertDto mindInsertDto) {
        return Mindmap.builder()
                .bookroomId(mindInsertDto.getBookroomId())
                .wordPictureUrl(mindInsertDto.getWordPictureUrl())
                .wordVoiceUrl(mindInsertDto.getWordVoiceUrl())
                .wordText(mindInsertDto.getWordText())
                .priority(mindInsertDto.getPriority())
                .build();
    }
}
