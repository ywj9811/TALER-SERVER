package com.demo.dto;

import com.demo.domain.Mindmap;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MindInsertDto {
    Long bookroomId;

    String wordPictureUrl;

    String wordVoiceUrl;

    String wordText;

    int priority;

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
