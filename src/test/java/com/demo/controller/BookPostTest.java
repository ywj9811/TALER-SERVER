package com.demo.controller;

import com.demo.domain.Mindmap;
import com.demo.domain.Picturetable;
import com.demo.domain.Wordtable;
import com.demo.dto.MindInsertDto;
import com.demo.dto.PictureInsertDto;
import com.demo.dto.WordInsertDto;
import com.demo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
public class BookPostTest {
    @Autowired
    BookService bookService;

    @Test
    void savePicture() {
        List<Picturetable> originPicturetables = bookService.getPictureByBookroomId(1L);
        int size = originPicturetables.size();
        log.info("기존의 크기 = {}", size);
        PictureInsertDto pictureInsertDto = new PictureInsertDto("abcd123.png", 1L);
        bookService.savePicture(pictureInsertDto);
        List<Picturetable> picturetables = bookService.getPictureByBookroomId(1L);
        assertThat(picturetables.size()).isEqualTo(size+1);
        log.info("실행 이후 크기 = {}", picturetables.size());
        assertThat(picturetables.get(size).getPictureUrl()).isEqualTo("abcd123.png");
    }

    @Test
    void saveWord() {
        List<Wordtable> originWord = bookService.getWordByroomId(1L);
        int size = originWord.size();

        log.info("기존의 크기 = {}", size);

        WordInsertDto wordInsertDto = new WordInsertDto(1L, 1L, "abc123abc", "", "", 1);
        bookService.saveWord(wordInsertDto);

        List<Wordtable> wordtables = bookService.getWordByroomId(1L);

        assertThat(wordtables.size()).isEqualTo(size+1);
        log.info("실행 이후의 크기 = {}", wordtables.size());
        assertThat(wordtables.get(size).getWordPictureUrl()).isEqualTo("abc123abc");
    }

    @Test
    void saveMind() {
        List<Mindmap> originMind = bookService.getMindmapByBookroomId(1L);
        int size = originMind.size();

        log.info("기존의 크기 = {}", size);

        MindInsertDto mindInsertDto = new MindInsertDto(1L, "123abc", "", "",  2);
        bookService.saveMind(mindInsertDto);

        List<Mindmap> mindmaps = bookService.getMindmapByBookroomId(1L);
        assertThat(mindmaps.size()).isEqualTo(size+1);
        log.info("실행 이후의 크기 = {}", mindmaps.size());
        assertThat(mindmaps.get(size).getPriority()).isEqualTo(2);
    }
}
