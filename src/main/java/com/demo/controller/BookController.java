package com.demo.controller;

import com.demo.domain.Mindmap;
import com.demo.domain.Picturetable;
import com.demo.domain.Wordtable;
import com.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @GetMapping("/bookroom")
    public Map<String, Object> moveToBookRoom(Long userId, Long bookId) {
//        return bookService.moveToBookRoom(bookId, userId);
        Map<String, Object> returnMap = bookService.getBookRoom(bookId, userId);

        return returnMap;
    }

    @GetMapping("/picture")
    public List<Picturetable> moveToPictureTab(Long bookroomId) {
        List<Picturetable> picturetables = bookService.getPictureByBookroomId(bookroomId);
        return picturetables;
    }
    @GetMapping("/word")
    public List<Wordtable> moveToWordTab(Long bookroomId) {
        List<Wordtable> wordtables = bookService.getWordByroomId(bookroomId);
        return wordtables;
    }
    @GetMapping("/mind")
    public List<Mindmap> moveToMindTab(Long bookroomId) {
        List<Mindmap> mindmaps = bookService.getMindmapByBookroomId(bookroomId);
        return mindmaps;
    }
}
/**
 * ----------설명------------
 * bookroom에 접근하면 Map에 각각의 객체를 담아서 반환함
 * key : bookroom 는 bookroom객체
 * key : bookdetails 는 bookdetails객체를 가지고 있음
 * 만약 잘못된 데이터를 통해서 접근하게 된다면 null을 반환함
 * 
 * picture, word, mind 탭에 접근하는 경우 접근시 아무것도 없을 경우 Empty로 반환이 됨
 * 존재한다면 모두 List에 담아서 반환하고 있음
 */
