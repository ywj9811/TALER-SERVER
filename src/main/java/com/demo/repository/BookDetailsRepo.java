package com.demo.repository;

import com.demo.domain.Bookdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookDetailsRepo extends JpaRepository<Bookdetails, Long> {
}
