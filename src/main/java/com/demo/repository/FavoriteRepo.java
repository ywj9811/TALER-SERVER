package com.demo.repository;

import com.demo.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
    Optional<Favorite> findByUserIdAndBookroomId(Long userId, Long bookroomId);

    @Query("SELECT fv.bookroomId FROM Favorite fv WHERE fv.userId = :userId AND fv.isfavorite = 1")
    List<Long> findBookroomIdByUserId(@Param("userId") Long userId);

    //좋아요 누르면 특정 isint = 1로 세팅
    @Query("UPDATE Favorite SET isfavorite =1 WHERE userId = :userId and bookId = :bookId")
    Favorite Like(@Param("userId")Long userId, @Param("bookId")Long bookId);

    //좋아요 취소하면 isint = 0으로 세팅
    @Query("UPDATE Favorite SET isfavorite = 0 WHERE userId = :userId and bookId = :bookId")
    Favorite DisLike(@Param("userId")Long userId, @Param("bookId")Long bookId);

    void deleteByUserIdAndBookId(Long userId, Long bookId);

    void deleteAllByBookroomId(Long bookroomId);
}
