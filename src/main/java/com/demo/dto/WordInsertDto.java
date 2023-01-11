package com.demo.dto;

import com.demo.domain.Wordtable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class WordInsertDto {
    Long bookroomId, mainId;
    String wordPictureUrl, wordVoiceUrl, wordText;
    int wordMain;

    public Wordtable insertDtoToWordtable(WordInsertDto wordInsertDto) {
        return Wordtable.builder()
                .wordMain(wordInsertDto.getWordMain())
                .wordText(wordInsertDto.getWordText())
                .wordPictureUrl(wordInsertDto.getWordPictureUrl())
                .wordVoiceUrl(wordInsertDto.getWordVoiceUrl())
                .bookroomId(wordInsertDto.getBookroomId())
                .mainId(wordInsertDto.getMainId())
                .build();
    }
}
