package com.demo.repository;

import com.demo.domain.Mindmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MindMapRepo extends JpaRepository<Mindmap, Long> {
    List<Mindmap> findAllByBookroomId(Long bookroomId);

    void deleteAllByBookroomId(Long bookroomId);
}
