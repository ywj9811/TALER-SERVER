package com.demo.repository;

import com.demo.domain.Wordtable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepo extends JpaRepository<Wordtable, Long> {
    List<Wordtable> findAllByBookroomId(Long bookroomId);

    void deleteAllByBookroomId(Long bookroomId);
}
