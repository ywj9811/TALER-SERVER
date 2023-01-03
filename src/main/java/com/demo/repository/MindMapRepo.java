package com.demo.repository;

import com.demo.domain.Mindmap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MindMapRepo extends JpaRepository<Mindmap, Long> {
}
