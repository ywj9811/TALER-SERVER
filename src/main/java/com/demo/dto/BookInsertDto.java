package com.demo.dto;

import com.demo.domain.Bookdetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookInsertDto {
    private String bookTitle;

    private String bookAuthor;

    public Bookdetails dtoToBookdetails() {
        return Bookdetails.builder()
                .bookTitle(this.bookTitle)
                .bookAuthor(this.bookAuthor)
                .build();
    }
}
