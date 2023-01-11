package com.demo.dto;

import com.demo.domain.Favorite;

public class DefaultFavoriteInsert {
    public static Favorite dtoToEntity(Long userId, Long bookId, Long bookroomId) {
        return Favorite.builder()
                .userId(userId)
                .bookId(bookId)
                .bookroomId(bookroomId)
                .isfavorite(0)
                .build();
    }
}
