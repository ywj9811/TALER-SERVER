package com.demo.repository;

import com.demo.domain.Bookroom;
import com.demo.domain.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    Optional<User> findByNickname(String nickname);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Parent p SET p.userId = :userId where p.parentId = :parentId")
    void updateUserId(@Param(value="userId") long userId, @Param(value="parentId") long parentId);

}
