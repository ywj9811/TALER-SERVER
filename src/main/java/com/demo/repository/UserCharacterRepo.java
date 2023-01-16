package com.demo.repository;

import com.demo.domain.Usercharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCharacterRepo extends JpaRepository<Usercharacter, Long> {
    Optional<Usercharacter> findByUserIdAndBookId(Long userId, Long bookId);
    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
/**
 * Optional을 사용한 이유는 예외처리를 위해서 사용한 것으로 Usercharacter객체가 필요하면 결과.get()하면 됩니다
 */