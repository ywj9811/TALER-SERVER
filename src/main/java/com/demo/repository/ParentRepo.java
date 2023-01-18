package com.demo.repository;

import com.demo.domain.Parent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParentRepo extends JpaRepository<Parent, Long> {
    Optional<Parent> findByNickname(String nickname);
}
