package com.demo.controller;

import com.demo.domain.*;
import com.demo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Transactional
class BookGetTest {

    @Autowired
    BookService bookService;

    @Test
    void bookroomSuccess() {
        Roomview bookRoom = bookService.getBookRoom(1L, 1L);
        log.info("bookroomId = {}", bookRoom.getBookroomId());
        log.info("bookId = {}", bookRoom.getBookId());
        log.info("userId = {}", bookRoom.getUserId());
        log.info("characterId = {}", bookRoom.getCharacterId());
        log.info("themeColor = {}", bookRoom.getThemeColor());
        log.info("themeMusicUrl = {}", bookRoom.getThemeMusicUrl());
        log.info("--------------------------------------------");
        log.info("bookId = {}", bookRoom.getBookTitle());
    }

    @Test
    void bookroomfailByBookId() {
        Roomview bookRoom = bookService.getBookRoom(3L, 1L);
        assertThat(bookRoom).isNull();
    }

    @Test
    void bookroomfailByUserId() {
        Roomview bookRoom = bookService.getBookRoom(1L, 4L);
        assertThat(bookRoom).isNull();
    }

    @Test
    void moveToPictureTab() {
        List<Picturetable> pictures = bookService.getPictureByBookroomId(1L);
        assertThat(pictures.size()).isEqualTo(4);
    }

    @Test
    void emptyPictureTab() {
        List<Picturetable> pictures = bookService.getPictureByBookroomId(3L);
        assertThat(pictures).isEmpty();
    }

    @Test
    void moveToWordTab() {
        List<Wordtable> words = bookService.getWordByroomId(1L);
        assertThat(words.size()).isEqualTo(2);
    }

    @Test
    void emptyWordTab() {
        List<Wordtable> words = bookService.getWordByroomId(3L);
        assertThat(words).isEmpty();
    }

    @Test
    void moveToMindTab() {
        List<Mindmap> mindmaps = bookService.getMindmapByBookroomId(1L);
        assertThat(mindmaps.size()).isEqualTo(1);
    }

    @Test
    void emptyMindTab() {
        List<Mindmap> mindmaps = bookService.getMindmapByBookroomId(3L);
        assertThat(mindmaps).isEmpty();
    }
}