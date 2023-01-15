package com.demo.repository;

import com.demo.domain.Bookroom;
import com.demo.domain.User;
import com.demo.dto.UserLogInDto.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    boolean existsByNickname(String nickname);
    User findUserByNickname(String nickName);

}
