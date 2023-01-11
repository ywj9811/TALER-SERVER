package com.demo.dto;

import com.demo.domain.Picturetable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PictureInsertDto {
    String pictureUrl;
    Long bookroomId;

    public Picturetable insertDtoToPicturetable(PictureInsertDto pictureInsertDto) {
        return Picturetable.builder()
                .bookroomId(pictureInsertDto.getBookroomId())
                .pictureUrl(pictureInsertDto.getPictureUrl())
                .build();
    }
}
