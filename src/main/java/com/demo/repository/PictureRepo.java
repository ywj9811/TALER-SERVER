package com.demo.repository;

import com.demo.domain.Picturetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PictureRepo extends JpaRepository<Picturetable, Long> {
    List<Picturetable> findAllByBookroomId(Long bookroomId);

    void deleteByBookroomId(Long bookroomId);
}
