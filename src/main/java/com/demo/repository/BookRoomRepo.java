package com.demo.repository;

import com.demo.domain.Bookroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRoomRepo extends JpaRepository<Bookroom, Long> {
    Bookroom findByUserIdAndBookId();
}
