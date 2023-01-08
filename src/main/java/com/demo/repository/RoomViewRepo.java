package com.demo.repository;

import com.demo.domain.Roomview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomViewRepo extends JpaRepository<Roomview, Long> {
    Roomview findByBookIdAndUserId(Long bookId, Long userId);
}
