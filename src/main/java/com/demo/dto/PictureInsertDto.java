package com.demo.dto;

import com.demo.domain.Picturetable;
import lombok.Data;

@Data
public class PictureInsertDto {
    String pictureUrl;
    Long bookroomId;

    public PictureInsertDto(String pictureUrl, Long bookroomId) {
        this.pictureUrl = pictureUrl;
        this.bookroomId = bookroomId;
    }

    public Picturetable insertDtoToPicturetable(PictureInsertDto pictureInsertDto) {
        return Picturetable.builder()
                .bookroomId(pictureInsertDto.getBookroomId())
                .pictureUrl(pictureInsertDto.getPictureUrl())
                .build();
    }
}
