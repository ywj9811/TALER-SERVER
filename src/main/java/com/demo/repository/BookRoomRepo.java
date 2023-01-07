package com.demo.repository;

import com.demo.domain.Bookroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRoomRepo extends JpaRepository<Bookroom, Long> {

    Optional<Bookroom> findByUserIdAndBookId(Long bookId, Long userId);

//    @Query("select bd, br from Bookdetails bd JOIN Bookroom br ON br.bookId = br.bookId where br.userId = :userId")
//    BookRoomPlusBookDetails findBookTitle(@Param("userId") Long userId);
    
}
