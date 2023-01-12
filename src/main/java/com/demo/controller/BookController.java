package com.demo.controller;

import com.demo.domain.*;
import com.demo.dto.BookRoomInsertDto;
import com.demo.dto.MindInsertDto;
import com.demo.dto.PictureInsertDto;
import com.demo.dto.WordInsertDto;
import com.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final BookService bookService;

    @GetMapping("/bookroom") //bookroom 조회
    public Roomview moveToBookRoom(Long userId, Long bookId) {
        Roomview bookRoom = bookService.selectBookRoom(bookId, userId);
        return bookRoom;
    }
    /**
     * View를 만들어서 사용함 (join대신)
     */
    /**
     * join을 사용할 경우
     * 결과값이 안나옴 null로 나옴
     * 나중에 이유를 알게 되면 고치도록 하자.
     * 우선 작동하는 방식으로 하자.
     */

    @PostMapping("/bookroom") //bookroom 생성
    public Bookroom saveBookRoom(BookRoomInsertDto bookRoomInsertDto) {
        Bookroom bookroom = bookService.saveBookRoom(bookRoomInsertDto);
        return bookroom;
    }
    /**
     * Post/bookroom 실행시
     * bookroom 생성
     * 기본 usercharacter 베이스로 bookroom 캐릭터 생성
     * favorite 좋아요X 생성
     * bookdetails의 bookpopularity 증가
     *
     */

    @PostMapping("/bookroom/color")
    public void updateThemeColor(Long bookroomId, String themeColor) {
        bookService.updateThemeColor(themeColor, bookroomId);
    }

    @PostMapping("/bookroom/music")
    public void updateThemeMusicUrl(Long bookroomId, String themeMusicUrl) {
        bookService.updateThemeMusicUrl(themeMusicUrl, bookroomId);
    }

    @PostMapping("/bookroom/delete")
    public void deleteBookroom(Long bookroomId) {
        bookService.deleteBookRoom(bookroomId);
    }

    @GetMapping("/picture") //pciture탭 조회
    public List<Picturetable> moveToPictureTab(Long bookroomId) {
        List<Picturetable> picturetables = bookService.getPictureByBookroomId(bookroomId);
        return picturetables;
    }

    @PostMapping("/picture") //picture 등록
    public Picturetable savePicture(PictureInsertDto pictureInsertDto) {
        Picturetable picturetable = bookService.savePicture(pictureInsertDto);
        return picturetable;
    }

    @GetMapping("/word") //word 조회
    public List<Wordtable> moveToWordTab(Long bookroomId) {
        List<Wordtable> wordtables = bookService.getWordByroomId(bookroomId);
        return wordtables;
    }
    @PostMapping("/word") //picture 등록
    public Wordtable saveWord(WordInsertDto wordInsertDto) {
        Wordtable wordtable = bookService.saveWord(wordInsertDto);
        return wordtable;
    }

    @GetMapping("/mind") //mindmap 조회
    public List<Mindmap> moveToMindTab(Long bookroomId) {
        List<Mindmap> mindmaps = bookService.getMindmapByBookroomId(bookroomId);
        return mindmaps;
    }
    @PostMapping("/mind") //mindmap 등록
    public Mindmap saveMind(MindInsertDto mindInsertDto) {
        Mindmap mindmap = bookService.saveMind(mindInsertDto);
        return mindmap;
    }
}
/**
 * ----------설명------------
 * bookroom에 접근하면 Roomview에 담아서 반환함
 * Roomview에는 bookroom의 모든 필드와 bookTitle, userCharacter의 필드, isfavorite 이 들어있다.
 * 만약 없다면 null이 반환됨
 * 
 * picture, word, mind 탭에 접근하는 경우 접근시 아무것도 없을 경우 Empty로 반환이 됨 이들은 List반환
 * 존재한다면 모두 List에 담아서 반환하고 있음
 */
