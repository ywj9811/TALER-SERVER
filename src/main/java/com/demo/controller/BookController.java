package com.demo.controller;

import com.demo.dto.MindInsertDto;
import com.demo.dto.PictureInsertDto;
import com.demo.dto.WordInsertDto;
import com.demo.dto.response.Response;
import com.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final BookService bookService;

    //결과 코드, 결과 메시지, 결과 -> Map 사용
    @GetMapping("/bookroom/{userId}/{bookId}") //bookroom 조회
    public Response moveToBookRoom(@PathVariable String userId, @PathVariable String bookId) {
        if (userId == null || bookId == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.selectBookRoom(Long.parseLong(bookId), Long.parseLong(userId));
        } catch (Exception e) {
            return new Response(BOOKROOMSELECTERRORMESSAGE, BOOKROOMSELECTERRORCODE);
        }
    }

    @PostMapping("/bookroom/{userId}") //bookroom 생성
    public Response saveBookRoom(@PathVariable String userId, String bookTitle, String bookAuthor) {
        try {
            return bookService.saveBookRoom(Long.parseLong(userId), bookTitle, bookAuthor);
        } catch (Exception e) {
            return new Response(BOOKROOMINSERTERRORMESSAGE, BOOKROOMINSERTERRORCODE);
        }
    }

    @PutMapping("/bookroom/color/{bookroomId}")
    public Response updateThemeColor(@PathVariable String bookroomId, String themeColor) {
        if (bookroomId == null || themeColor == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.updateThemeColor(themeColor, Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(BOOKROOMUPDATEERRORMESSAGE, BOOKROOMUPDATEERRORCODE);
        }
    }

    @PutMapping("/bookroom/music/{bookroomId}")
    public Response updateThemeMusicUrl(@PathVariable String bookroomId, String themeMusicUrl) {
        if (bookroomId == null || themeMusicUrl == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.updateThemeMusicUrl(themeMusicUrl, Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(BOOKROOMUPDATEERRORMESSAGE, BOOKROOMUPDATEERRORCODE);
        }
    }

    @DeleteMapping("/bookroom/delete/{bookroomId}")
    public Response deleteBookroom(@PathVariable String bookroomId) {
        if (bookroomId == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.deleteBookRoom(Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(BOOKROOMDELETEERRORMESSAGE, BOOKROOMDELETEERRORCODE);
        }
    }

    @GetMapping("/picture/{bookroomId}") //pciture탭 조회
    public Response moveToPictureTab(@PathVariable String bookroomId) {
        if (bookroomId == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.getPictureByBookroomId(Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(PICTURESELECTERRORMESSAGE, PICTURESELECTERRORCODE);
        }
    }

    @PostMapping("/picture/{bookroomId}") //picture 등록
    public Response savePicture(String pictureUrl, @PathVariable String bookroomId) {
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

    @GetMapping("/word/{bookroomId}") //word 조회
    public Response moveToWordTab(@PathVariable String bookroomId) {
        if (bookroomId == null) {
            return new Response(NULLMESSAGE, NULLCODE);
        }
        try {
            return bookService.getWordByroomId(Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(WORDSELECTERRORMESSAGE, WORDSELECTERRORCODE);
        }
    }

    @PostMapping("/word/{bookroomId}") //word 등록
    public Response saveWord(@PathVariable String bookroomId, String mainId, String wordPictureUrl, String wordVoiceUrl, String wordText, String wordMain) {
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

    @GetMapping("/mind/{bookroomId}") //mindmap 조회
    public Response moveToMindTab(@PathVariable String bookroomId) {
        if (bookroomId == null) {
            return new Response(BOOKROOMUPDATEERRORMESSAGE, BOOKROOMUPDATEERRORCODE);
        }
        try {
            return bookService.getMindmapByBookroomId(Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(MINDMAPSELECTERRORMESSAGE, MINDMAPINSERTERRORCODE);
        }
    }

    @PostMapping("/mind/{bookroomId}") //mindmap 등록
    public Response saveMind(@PathVariable String bookroomId, String wordPictureUrl, String wordVoiceUrl, String wordText, String priority) {
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

    @PutMapping("/picture/{pictureId}")
    public Response updatePicture(@PathVariable String pictureId, String pictureUrl) {
        try {
            if (pictureUrl == null)
                return new Response(NULLMESSAGE, NULLCODE);
            return bookService.updatePicture(Long.parseLong(pictureId), pictureUrl);
        } catch (Exception e) {
            return new Response(PICTUREUPDATEERRORMESSAGE, PICTUREINSERTERRORCODE);
        }
    }

    @PutMapping("/word/{wordId}")
    public Response updateWord(@PathVariable String wordId, String wordPictureUrl, String wordVoiceUrl, String wordText) {
        try {
            if (wordText == null && wordPictureUrl == null && wordVoiceUrl == null)
                return new Response(NULLMESSAGE, NULLCODE);
            return bookService.updateWord(Long.parseLong(wordId), wordText, wordPictureUrl, wordVoiceUrl);
        } catch (Exception e) {
            return new Response(WORDUPDATEERRORMESSAGE, WORDUPDATEERRORCODE);
        }
    }

    @PutMapping("/mind/{mindId}")
    public Response updateMind(@PathVariable String mindId, String wordPictureUrl, String wordVoiceUrl, String wordText) {
        try {
            if (wordText == null && wordPictureUrl == null && wordVoiceUrl == null)
                return new Response(NULLMESSAGE, NULLCODE);
            return bookService.updateMind(Long.parseLong(mindId), wordText, wordPictureUrl, wordVoiceUrl);
        } catch (Exception e) {
            return new Response(MINDMAPUPDATEERRORMESSAGE, MINDMAPUPDATEERRORCODE);
        }
    }

    @DeleteMapping("/picture/{pictureId}")
    public Response deletePicture(@PathVariable String pictureId) {
        try {
            return bookService.deletePicture(Long.parseLong(pictureId));
        } catch (Exception e) {
            return new Response(PICTUREDELETEERRORMESSAGE, PICTUREDELETEERRORCODE);
        }
    }

    @DeleteMapping("/word/{wordId}")
    public Response deleteWord(@PathVariable String wordId) {
        try {
            return bookService.deleteWord(Long.parseLong(wordId));
        } catch (Exception e) {
            return new Response(WORDDELETEERRORMESSAGE, WORDDELETEERRORCODE);
        }
    }

    @DeleteMapping("/mind/{mindId}")
    public Response deleteMind(@PathVariable String mindId) {
        try {
            return bookService.deleteMind(Long.parseLong(mindId));
        } catch (Exception e) {
            return new Response(MINDMAPDELETEERRORMESSAGE, MINDMAPDELETEERRORCODE);
        }
    }
}
