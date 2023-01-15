package com.demo.service;

import com.demo.domain.*;
import com.demo.domain.config.BaseException;
import com.demo.domain.config.BaseResponse;
import com.demo.dto.*;
import com.demo.dto.response.Response;
import com.demo.dto.response.BException;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.demo.domain.config.BaseResponseStatus.*;
import static com.demo.domain.responseCode.ResponseCodeMessage.*;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepo userRepo;
    @Autowired
    private final ParentRepo parentRepo;
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

    public Parent saveParent(ParentInsertDto parentInsertDto){
        String encodePw = passwordEncoder.encode(parentInsertDto.getPw());
        parentInsertDto.setPw(encodePw);
        Parent parent = parentInsertDto.dtoToParent(parentInsertDto);
        Parent saveParent = parentRepo.save(parent);
        return saveParent;

    }

    public Response registerUser(LogInDto logInDto) throws BException {
        User user = userRepo.findByNickname(logInDto.getNickname());
        try{
            userRepo.updateUserId(user.getUserId(), logInDto.getNickname());

        }catch (Exception e){
            throw new BException(UPDATEERRORMESSAGE,UPDATEERRORCODE);
        }
        return response;

    }


    public boolean checkNickname(String nickname) throws BaseException {
        try {
            return userRepo.existsByNickname(nickname);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
