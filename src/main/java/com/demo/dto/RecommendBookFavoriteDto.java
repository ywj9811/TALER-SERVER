package com.demo.dto;

import com.demo.domain.Bookroom;
import com.demo.domain.Favorite;
import lombok.Data;

import java.awt.print.Book;


@Data
public class RecommendBookFavoriteDto {
    Long userId;
    Long bookId;
    Long bookroomId;
    String themeColor;

    public RecommendBookFavoriteDto(Long userId, Long bookId, Long bookroomId, String themeColor) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookroomId = bookroomId;
        this.themeColor = themeColor;
    }
}
