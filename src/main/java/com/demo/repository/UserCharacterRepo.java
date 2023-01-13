package com.demo.repository;

import com.demo.domain.Usercharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCharacterRepo extends JpaRepository<Usercharacter, Long> {
    Usercharacter findByUserIdAndBookId(Long userId, Long bookId);
    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
