package com.demo.dto;

import com.demo.domain.Wordtable;
import lombok.Data;

@Data
public class WordInsertDto {
    Long bookroomId, mainId;
    String wordPictureUrl, wordVoiceUrl, wordText;
    int wordMain;

    public WordInsertDto(Long bookroomId, Long mainId, String wordPictureUrl, String wordVoiceUrl, String wordText, int wordMain) {
        this.bookroomId = bookroomId;
        this.mainId = mainId;
        this.wordPictureUrl = wordPictureUrl;
        this.wordVoiceUrl = wordVoiceUrl;
        this.wordText = wordText;
        this.wordMain = wordMain;
    }

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
