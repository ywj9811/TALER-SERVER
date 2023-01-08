package com.demo.repository;

import com.demo.domain.BookRoomPlusBookDetails;
import com.demo.domain.Bookroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRoomRepo extends JpaRepository<Bookroom, Long> {

    Optional<Bookroom> findByUserIdAndBookId(Long bookId, Long userId);

//    @Query("select bd, br from Bookdetails bd JOIN Bookroom br ON br.bookId = bd.bookId where br.userId = :userId")
//    BookRoomPlusBookDetails findBookTitle(@Param("userId") Long userId);
    /**
     * 결과값이 안나옴 null로 나옴
     * 나중에 이유를 알게 되면 고치도록 하자.
     * 우선 작동하는 방식으로 하자.
     */
}
