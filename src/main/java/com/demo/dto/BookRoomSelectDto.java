package com.demo.dto;

import lombok.Data;


@Data
public class BookRoomSelectDto {
    Long userId;
    Long bookId;
    Long bookroomId;
    String themeColor;

    public BookRoomSelectDto(Long userId, Long bookId, Long bookroomId, String themeColor) {
        this.userId = userId;
        this.bookId = bookId;
        this.bookroomId = bookroomId;
        this.themeColor = themeColor;
    }
}
