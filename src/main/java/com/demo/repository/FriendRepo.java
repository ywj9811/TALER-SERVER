package com.demo.repository;

import com.demo.domain.Friend;
import com.demo.dto.FriendDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.LongSummaryStatistics;

@Repository
public interface FriendRepo extends JpaRepository<Friend, Long> {
    List<Friend> findAllByUserId(Long userId);

    @Query("INSERT Friend VALUES (:userId,:friendUserId)")
    List<Long>add(Long userId, Long friendUserId);
    @Query("DELETE FROM Freind WHERE userId = :userId and friendId = :friendId")
    List<Long>delete(Long userId, Long friendUserId);


    boolean existsByUserFriendIdAndUserId(Long friendId, Long UserId);
}
