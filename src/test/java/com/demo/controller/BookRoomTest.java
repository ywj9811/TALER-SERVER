package com.demo.controller;

import com.demo.domain.*;
import com.demo.dto.BookRoomInsertDto;
import com.demo.dto.MindInsertDto;
import com.demo.dto.PictureInsertDto;
import com.demo.dto.WordInsertDto;
import com.demo.repository.BookDetailsRepo;
import com.demo.repository.FavoriteRepo;
import com.demo.repository.UserCharacterRepo;
import com.demo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
@Transactional
public class BookRoomTest {
    @Autowired
    BookService bookService;
    @Autowired
    BookDetailsRepo bookDetailsRepo;
    @Autowired
    FavoriteRepo favoriteRepo;
    @Autowired
    UserCharacterRepo userCharacterRepo;

    @Test
    void savePicture() {
        log.info("첫번째 select 수행");
        List<Picturetable> originPicturetables = bookService.getPictureByBookroomId(1L);
        int size = originPicturetables.size();
        log.info("기존의 크기 = {}", size);
        
        log.info("Insert 수행");
        PictureInsertDto pictureInsertDto = new PictureInsertDto("abcd123.png", 1L);
        bookService.savePicture(pictureInsertDto);
        
        log.info("두번째 select 수행");
        List<Picturetable> picturetables = bookService.getPictureByBookroomId(1L);
        assertThat(picturetables.size()).isEqualTo(size+1);
        log.info("실행 이후 크기 = {}", picturetables.size());
        assertThat(picturetables.get(size).getPictureUrl()).isEqualTo("abcd123.png");
    }

    @Test
    void saveWord() {
        log.info("첫번째 select 수행");
        List<Wordtable> originWord = bookService.getWordByroomId(1L);
        int size = originWord.size();
        log.info("기존의 크기 = {}", size);
        
        log.info("Insert 수행");
        WordInsertDto wordInsertDto = new WordInsertDto(1L, 1L, "abc123abc", "", "", 1);
        bookService.saveWord(wordInsertDto);
    
        log.info("두번째 select 수행");
        List<Wordtable> wordtables = bookService.getWordByroomId(1L);
        assertThat(wordtables.size()).isEqualTo(size+1);
        log.info("실행 이후의 크기 = {}", wordtables.size());
        assertThat(wordtables.get(size).getWordPictureUrl()).isEqualTo("abc123abc");
    }

    @Test
    void saveMind() {
        log.info("첫번째 select 수행");
        List<Mindmap> originMind = bookService.getMindmapByBookroomId(1L);
        int size = originMind.size();
        log.info("기존의 크기 = {}", size);
        
        log.info("Insert 수행");
        MindInsertDto mindInsertDto = new MindInsertDto(1L, "123abc", "", "",  2);
        bookService.saveMind(mindInsertDto);
        
        log.info("두번째 select 수행");
        List<Mindmap> mindmaps = bookService.getMindmapByBookroomId(1L);
        assertThat(mindmaps.size()).isEqualTo(size+1);
        log.info("실행 이후의 크기 = {}", mindmaps.size());
        assertThat(mindmaps.get(size).getPriority()).isEqualTo(2);
    }

    @Test
    void saveBookRoom() {
        BookRoomInsertDto bookRoomInsertDto = new BookRoomInsertDto(1L, 3L, "red", "abfddf123");
        log.info("bookdetails의 popularity 첫번째 조회");
        Optional<Bookdetails> optionalBookdetails = bookDetailsRepo.findById(bookRoomInsertDto.getBookId());
        Bookdetails bookdetails = optionalBookdetails.get();
        int bookPopularity = bookdetails.getBookPopularity();
        log.info("popularity = {} ", bookPopularity);
        
        log.info("bookroom 추가");
        Bookroom bookroom = bookService.saveBookRoom(bookRoomInsertDto);

        Favorite favorite = favoriteRepo.findByUserIdAndBookId(bookroom.getUserId(), bookroom.getBookId());
        log.info("favorite 생성 확인, isfavorite = {}", favorite.getIsfavorite());
        assertThat(favorite.getIsfavorite()).isEqualTo(0);

        Usercharacter usercharacter = userCharacterRepo.findByUserIdAndBookId(bookroom.getUserId(), bookroom.getBookId());
        Usercharacter originalUsercharacter = userCharacterRepo.findByUserIdAndBookId(bookroom.getUserId(), 0L);
        log.info("userCharacter 생성 확인, bookId = {}", usercharacter.getBookId());
        assertThat(originalUsercharacter.getFaceColor()).isEqualTo(usercharacter.getFaceColor());
        assertThat(originalUsercharacter.getFaceStyle()).isEqualTo(usercharacter.getFaceStyle());
        assertThat(originalUsercharacter.getPantsColor()).isEqualTo(usercharacter.getPantsColor());
        assertThat(originalUsercharacter.getPantsStyle()).isEqualTo(usercharacter.getPantsStyle());

        Bookdetails afterBookdetails = bookDetailsRepo.findById(bookdetails.getBookId()).get();
        log.info("완료 후 popularity = {}", afterBookdetails.getBookPopularity());
        assertThat(afterBookdetails.getBookPopularity()).isEqualTo(bookPopularity + 1);
    }
}