package com.demo.controller;

import com.demo.domain.*;
import com.demo.dto.BookRoomInsertDto;
import com.demo.dto.MindInsertDto;
import com.demo.dto.PictureInsertDto;
import com.demo.dto.WordInsertDto;
import com.demo.dto.response.Response;
import com.demo.repository.*;
import com.demo.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

/**
 * 테스트 오류 다시 잡아야함
 */

@SpringBootTest
@Slf4j
@Transactional
public class BookRoomTest {
    @Autowired
    PictureRepo pictureRepo;
    @Autowired
    WordRepo wordRepo;
    @Autowired
    MindMapRepo mindMapRepo;
    @Autowired
    BookRoomRepo bookRoomRepo;
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
        Response response = new Response();
        log.info("첫번째 select 수행");
        Response responsePicture = bookService.getPictureByBookroomId(1L, response);
        List<Picturetable> originPicturetables = (List<Picturetable>) responsePicture.getResult();
        int size = originPicturetables.size();
        log.info("기존의 크기 = {}", size);

        log.info("Insert 수행");
        PictureInsertDto pictureInsertDto = new PictureInsertDto("abcd123.png", 1L);
        bookService.savePicture(pictureInsertDto, response);

        log.info("두번째 select 수행");
        Response responsePicture2 = bookService.getPictureByBookroomId(1L, response);
        List<Picturetable> picturetables = (List<Picturetable>) responsePicture2.getResult();
        assertThat(picturetables.size()).isEqualTo(size+1);
        log.info("실행 이후 크기 = {}", picturetables.size());
        assertThat(picturetables.get(size).getPictureUrl()).isEqualTo("abcd123.png");
    }

    @Test
    void saveWord() {
        Response response = new Response();
        log.info("첫번째 select 수행");
        Response responseWord = bookService.getWordByroomId(1L, response);
        List<Wordtable> originWord = (List<Wordtable>) responseWord.getResult();
        int size = originWord.size();
        log.info("기존의 크기 = {}", size);

        log.info("Insert 수행");
        WordInsertDto wordInsertDto = new WordInsertDto(1L, 1L, "abc123abc", "", "", 1);
        bookService.saveWord(wordInsertDto, response);

        log.info("두번째 select 수행");
        Response responseBooksroom = bookService.getWordByroomId(1L, response);
        List<Wordtable> wordtables = (List<Wordtable>) responseBooksroom.getResult();
        assertThat(wordtables.size()).isEqualTo(size+1);
        log.info("실행 이후의 크기 = {}", wordtables.size());
        assertThat(wordtables.get(size).getWordPictureUrl()).isEqualTo("abc123abc");
    }

    @Test
    void saveMind() {
        Response response = new Response();
        log.info("첫번째 select 수행");
        Response responseMind = bookService.getMindmapByBookroomId(1L, response);
        List<Mindmap> originMind = (List<Mindmap>) responseMind.getResult();
        int size = originMind.size();
        log.info("기존의 크기 = {}", size);

        log.info("Insert 수행");
        MindInsertDto mindInsertDto = new MindInsertDto(1L, "123abc", "", "", 2);
        bookService.saveMind(mindInsertDto, response);

        log.info("두번째 select 수행");
        Response responseMind2 = bookService.getMindmapByBookroomId(1L, response);
        List<Mindmap> mindmaps = (List<Mindmap>) responseMind2.getResult();
        assertThat(mindmaps.size()).isEqualTo(size+1);
        log.info("실행 이후의 크기 = {}", mindmaps.size());
        assertThat(mindmaps.get(size).getPriority()).isEqualTo(2);
    }

    @Test
    void saveBookRoom() {
        Response response = new Response();

        BookRoomInsertDto bookRoomInsertDto = new BookRoomInsertDto(1L, 4L);
        log.info("bookdetails의 popularity 첫번째 조회");
        Optional<Bookdetails> optionalBookdetails = bookDetailsRepo.findById(bookRoomInsertDto.getBookId());
        Bookdetails bookdetails = optionalBookdetails.get();
        int bookPopularity = bookdetails.getBookPopularity();
        log.info("popularity = {} ", bookPopularity);

        log.info("bookroom 추가");
        response = bookService.saveBookRoom(bookRoomInsertDto, response);
        Bookroom bookroom = (Bookroom) response.getResult();

        Favorite favorite = favoriteRepo.findByUserIdAndBookId(bookroom.getUserId(), bookroom.getBookId());
        log.info("favorite 생성 확인, isfavorite = {}", favorite.getIsfavorite());
        assertThat(favorite.getIsfavorite()).isEqualTo(0);

        Usercharacter usercharacter = userCharacterRepo.findByUserIdAndBookId(bookroom.getUserId(), bookroom.getBookId()).get();
        Usercharacter originalUsercharacter = userCharacterRepo.findByUserIdAndBookId(bookroom.getUserId(), 0L).get();
        log.info("userCharacter 생성 확인, bookId = {}", usercharacter.getBookId());
        assertThat(originalUsercharacter.getFaceColor()).isEqualTo(usercharacter.getFaceColor());
        assertThat(originalUsercharacter.getFaceStyle()).isEqualTo(usercharacter.getFaceStyle());
        assertThat(originalUsercharacter.getPantsColor()).isEqualTo(usercharacter.getPantsColor());
        assertThat(originalUsercharacter.getPantsStyle()).isEqualTo(usercharacter.getPantsStyle());

        Bookdetails afterBookdetails = bookDetailsRepo.findById(bookdetails.getBookId()).get();
        log.info("완료 후 popularity = {}", afterBookdetails.getBookPopularity());
        assertThat(afterBookdetails.getBookPopularity()).isEqualTo(bookPopularity + 1);
    }

    @Test
    void deleteBookRoom() {
        Response response = new Response();
        BookRoomInsertDto bookRoomInsertDto = new BookRoomInsertDto(1L, 4L);
        Response bookResult = bookService.saveBookRoom(bookRoomInsertDto, response);
        Bookroom bookroom = (Bookroom) bookResult.getResult();
        log.info("bookroom하나 생성(bookId = 4)(userId = 1) {}");
        log.info("bookroomId = {}", bookroom.getBookroomId());

        List<Bookroom> originAll = bookRoomRepo.findAll();
        int originSize = originAll.size();
        int pictureSize = getPictureSize(bookroom);
        int wordSize = getWordSize(bookroom);
        int mindSize = getMindSize(bookroom);
        List<Usercharacter> originCharacterAll = userCharacterRepo.findAll();
        int characterSize = originCharacterAll.size();

        bookService.deleteBookRoom(bookroom.getBookroomId(), response);

        assertThat(bookRoomRepo.findAll().size()).isEqualTo(originSize - 1);
        assertThat(wordRepo.findAll().size()).isEqualTo(wordSize - 1);
        assertThat(mindMapRepo.findAll().size()).isEqualTo(mindSize - 1);
        assertThat(pictureRepo.findAll().size()).isEqualTo(pictureSize - 1);
        assertThat(userCharacterRepo.findAll().size()).isEqualTo(characterSize - 1);
    }

    private int getMindSize(Bookroom bookroom) {
        Response response = new Response();
        MindInsertDto mindInsertDto = new MindInsertDto(bookroom.getBookroomId(), "", "", "good", 1);
        Response responseMind = bookService.saveMind(mindInsertDto, response);
        Mindmap mindmap = (Mindmap) responseMind.getResult();
        List<Mindmap> originMindAll = mindMapRepo.findAll();
        return originMindAll.size();
    }

    private int getWordSize(Bookroom bookroom) {
        Response response = new Response();
        WordInsertDto wordInsertDto = new WordInsertDto(bookroom.getBookroomId(), 1L, "", "", "good", 1);
        Response responseWord = bookService.saveWord(wordInsertDto, response);
        Wordtable wordtable = (Wordtable) responseWord.getResult();
        List<Wordtable> originWordAll = wordRepo.findAll();
        return originWordAll.size();
    }

    private int getPictureSize(Bookroom bookroom) {
        Response response = new Response();
        PictureInsertDto pictureInsertDto = new PictureInsertDto("asfdv", bookroom.getBookroomId());
        Response responsePicture = bookService.savePicture(pictureInsertDto, response);
        Picturetable picturetable = (Picturetable) responsePicture.getResult();
        log.info("들어감? = {}", picturetable.getPictureUrl());

        List<Picturetable> originPictureAll = pictureRepo.findAll();
        return originPictureAll.size();
    }
}
