package com.demo.repository;

import com.demo.domain.Bookdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookDetailsRepo extends JpaRepository<Bookdetails, Long> {
    Optional<Bookdetails> findByBookTitleAndBookAuthor(String bookTitle, String bookAuthor);
}
