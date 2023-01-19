package com.demo.repository;

import com.demo.domain.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepo extends JpaRepository<Friend, Long> {
    List<Friend> findAllByUserId(Long userId);

    boolean existsByUserFriendIdAndUserId(Long friendId, Long UserId);
}
