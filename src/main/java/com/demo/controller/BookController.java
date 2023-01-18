package com.demo.controller;

import com.demo.domain.*;
import com.demo.dto.BookRoomInsertDto;
import com.demo.dto.MindInsertDto;
import com.demo.dto.PictureInsertDto;
import com.demo.dto.WordInsertDto;
import com.demo.dto.response.Response;
import com.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final BookService bookService;
    //결과 코드, 결과 메시지, 결과 -> Map 사용
    @GetMapping("/bookroom") //bookroom 조회
    public Response moveToBookRoom(String userId, String bookId) {
        if (userId == null || bookId == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.selectBookRoom(Long.parseLong(bookId), Long.parseLong(userId));
        } catch (Exception e) {
            return new Response(BOOKROOMSELECTERRORMESSAGE, BOOKROOMSELECTERRORCODE);
        }
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
    public Response saveBookRoom(String userId, String bookId) {
        try {
            BookRoomInsertDto bookRoomInsertDto = new BookRoomInsertDto(Long.parseLong(userId), Long.parseLong(bookId));
            log.info("getBookId = {}", bookRoomInsertDto.getBookId());
            if (bookRoomInsertDto.getBookId() == null || bookRoomInsertDto.getBookId() == null) {
                return new Response(NULLMESSAGE, NULLCODE);
            }
            return bookService.saveBookRoom(bookRoomInsertDto);
        } catch (Exception e) {
            return new Response(BOOKROOMINSERTERRORMESSAGE, BOOKROOMINSERTERRORCODE);
        }
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
    public Response updateThemeColor(String bookroomId, String themeColor) {
        if (bookroomId == null || themeColor == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.updateThemeColor(themeColor, Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(BOOKROOMUPDATEERRORMESSAGE, BOOKROOMUPDATEERRORCODE);
        }
    }

    @PostMapping("/bookroom/music")
    public Response updateThemeMusicUrl(String bookroomId, String themeMusicUrl) {
        if (bookroomId == null || themeMusicUrl == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.updateThemeMusicUrl(themeMusicUrl, Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(BOOKROOMUPDATEERRORMESSAGE, BOOKROOMUPDATEERRORCODE);
        }
    }

    @PostMapping("/bookroom/delete")
    public Response deleteBookroom(String bookroomId) {
        if (bookroomId == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.deleteBookRoom(Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(BOOKROOMDELETEERRORMESSAGE, BOOKROOMDELETEERRORCODE);
        }
    }

    @GetMapping("/picture") //pciture탭 조회
    public Response moveToPictureTab(String bookroomId) {
        if (bookroomId == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.getPictureByBookroomId(Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(PICTURESELECTERRORMESSAGE, PICTURESELECTERRORCODE);
        }
    }

    @PostMapping("/picture") //picture 등록
    public Response savePicture(String pictureUrl, String bookroomId) {
        try {
            PictureInsertDto pictureInsertDto = new PictureInsertDto(pictureUrl, Long.parseLong(bookroomId));
            if (pictureInsertDto.getPictureUrl() == null || pictureInsertDto.getBookroomId() == null) {
                return new Response(BOOKROOMUPDATEERRORMESSAGE, BOOKROOMUPDATEERRORCODE);
            }
            return bookService.savePicture(pictureInsertDto);
        } catch (Exception e) {
            return new Response(PICTUREINSERTERRORMESSAGE, PICTUREINSERTERRORCODE);
        }
    }

    @GetMapping("/word") //word 조회
    public Response moveToWordTab(String bookroomId) {
        if (bookroomId == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.getWordByroomId(Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(WORDSELECTERRORMESSAGE, WORDSELECTERRORCODE);
        }
    }
    @PostMapping("/word") //word 등록
    public Response saveWord(String bookroomId, String mainId, String wordPictureUrl, String wordVoiceUrl, String wordText, String wordMain) {
        try {
            WordInsertDto wordInsertDto = new WordInsertDto(Long.parseLong(bookroomId), Long.parseLong(mainId), wordPictureUrl, wordVoiceUrl, wordText, Integer.parseInt(wordMain));
            if ((Integer) wordInsertDto.getWordMain() == null || wordInsertDto.getMainId() == null || wordInsertDto.getBookroomId() == null) {
                return new Response(NULLMESSAGE, NULLCODE);
            }
            if (wordInsertDto.getWordText() == null && wordInsertDto.getWordPictureUrl() == null && wordInsertDto.getWordVoiceUrl() == null) {
                return new Response(NULLMESSAGE, NULLCODE);
            }
            return bookService.saveWord(wordInsertDto);
        } catch (Exception e) {
            return new Response(WORDINSERTERRORMESSAGE, WORDINSERTERRORCODE);
        }
    }

    @GetMapping("/mind") //mindmap 조회
    public Response moveToMindTab(String bookroomId) {
        if (bookroomId == null) {
            return new Response(BOOKROOMUPDATEERRORMESSAGE, BOOKROOMUPDATEERRORCODE);
        }
        try {
            return bookService.getMindmapByBookroomId(Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(MINDMAPSELECTERRORMESSAGE, MINDMAPINSERTERRORCODE);
        }
    }
    @PostMapping("/mind") //mindmap 등록
    public Response saveMind(String bookroomId, String wordPictureUrl, String wordVoiceUrl, String wordText, String priority) {
        try {
            MindInsertDto mindInsertDto = new MindInsertDto(Long.parseLong(bookroomId), wordPictureUrl, wordVoiceUrl, wordText, Integer.parseInt(priority));
            if ((Integer) mindInsertDto.getPriority() == null || mindInsertDto.getBookroomId() == null) {
                return new Response(NULLMESSAGE, NULLCODE);
            }
            if (mindInsertDto.getWordText() == null && mindInsertDto.getWordPictureUrl() == null && mindInsertDto.getWordVoiceUrl() == null) {
                return new Response(NULLMESSAGE, NULLCODE);
            }

            return bookService.saveMind(mindInsertDto);
        } catch (Exception e) {
            return new Response(MINDMAPINSERTERRORMESSAGE, MINDMAPINSERTERRORCODE);
        }
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
