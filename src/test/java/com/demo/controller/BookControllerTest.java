package com.demo.controller;

import com.demo.domain.*;
import com.demo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class BookControllerTest {

    @Autowired
    BookService bookService;

    @Test
    void bookroomSuccess() {
        Map<String, Object> bookRoom = bookService.getBookRoom(1L, 1L);
        Bookroom bookroom = (Bookroom) bookRoom.get("bookroom");
        Bookdetails bookdetails = (Bookdetails) bookRoom.get("bookdetails");
        log.info("bookroomId = {}", bookroom.getBookroomId());
        log.info("bookId = {}", bookroom.getBookId());
        log.info("userId = {}", bookroom.getUserId());
        log.info("characterId = {}", bookroom.getCharacterId());
        log.info("themeColor = {}", bookroom.getThemeColor());
        log.info("themeMusicUrl = {}", bookroom.getThemeMusicUrl());
        log.info("--------------------------------------------");
        log.info("bookId = {}", bookdetails.getBookId());
        log.info("bookId = {}", bookdetails.getBookAuthor());
        log.info("bookId = {}", bookdetails.getBookGenre());
        log.info("bookId = {}", bookdetails.getBookTitle());
        log.info("bookId = {}", bookdetails.getBookPopularity());
        assertThat(bookRoom.size()).isEqualTo(2);
    }

    @Test
    void bookroomfailByBookId() {
        Map<String, Object> bookRoom = bookService.getBookRoom(3L, 1L);
        assertThat(bookRoom).isNull();
    }

    @Test
    void bookroomfailByUserId() {
        Map<String, Object> bookRoom = bookService.getBookRoom(1L, 4L);
        assertThat(bookRoom).isNull();
    }

    @Test
    void moveToPictureTab() {
        List<Picturetable> pictures = bookService.getPictureByBookroomId(1L);
        assertThat(pictures.size()).isEqualTo(3);
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