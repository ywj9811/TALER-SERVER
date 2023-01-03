package com.demo.repository;

import com.demo.domain.Wordtable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepo extends JpaRepository<Wordtable, Long> {
}
