package com.demo.dto;

import com.demo.domain.Bookroom;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRoomInsertDto {
    Long userId;
    Long bookId;

    public Bookroom dtoToBookRoom(BookRoomInsertDto bookRoomInsertDto) {
        return Bookroom.builder()
                .userId(bookRoomInsertDto.getUserId())
                .bookId(bookRoomInsertDto.getBookId())
                .build();
    }
}
