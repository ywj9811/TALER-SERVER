package com.demo.dto;

import com.demo.domain.Favorite;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FavoriteInsertDto {
    Long user_id;
    Long bookroom_id;

    Long book_id;
    int isfavorite;

    public FavoriteInsertDto(Long user_id, Long bookroom_id,Long book_id, int isfavorite) {
        this.user_id = user_id;
        this.bookroom_id = bookroom_id;
        this.book_id = book_id;
        this.isfavorite = isfavorite;
    }

    public Favorite FavoriteDtoToFavorite() {
        return Favorite.builder()
                .userId(user_id)
                .bookroomId(bookroom_id)
                .bookId(book_id)
                .isfavorite(isfavorite)
                .build();
    }

}
