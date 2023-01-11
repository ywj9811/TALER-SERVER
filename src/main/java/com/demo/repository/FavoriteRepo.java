package com.demo.repository;

import com.demo.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
    Favorite findByUserIdAndBookId(Long userId, Long bookId);
}
