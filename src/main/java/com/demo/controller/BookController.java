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
