package com.demo.service;

import com.demo.domain.*;
import com.demo.domain.config.BaseException;
import com.demo.dto.*;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.demo.domain.config.BaseResponseStatus.*;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    public User saveUser(UserInsertDto userInsertDto) throws BaseException {
        if(checkNickname(userInsertDto.getNickname())){
            throw new BaseException(Post_USERS_EXISTS_NICKNAME);
        }

        String encodedPw = passwordEncoder.encode(userInsertDto.getPw());
        userInsertDto.setPw(encodedPw);
        User user = userInsertDto.dtoToUser(userInsertDto);
        User saveUser = userRepo.save(user);
        return saveUser;
    }

    public boolean checkNickname(String nickname) throws BaseException {
        try {
            return userRepo.existsByNickname(nickname);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public User userLogIn(String nickname) throws BaseException {

        try {
            User user = userRepo.findUserByNickname(nickname);
            //UserLogInResponse userLogInResponse = UserLogInResponse.builder().user(user).build();
            return user;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
