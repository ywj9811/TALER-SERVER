package com.demo.controller;

import com.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
