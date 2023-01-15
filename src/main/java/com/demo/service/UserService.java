package com.demo.service;

import com.demo.domain.*;
import com.demo.dto.*;
import com.demo.dto.response.Response;
import com.demo.dto.response.BaseException;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    public Response saveUser(UserInsertDto userInsertDto) throws BaseException {
        if(checkNickname(userInsertDto.getNickname())){
            throw new BaseException(USERSEXISTSNICKNAME,UPDATEERRORCODE);
        }

        String encodedPw = passwordEncoder.encode(userInsertDto.getPw());
        userInsertDto.setPw(encodedPw);
        User user = userInsertDto.dtoToUser(userInsertDto);
        User saveUser = userRepo.save(user);
        return new Response(saveUser,SUCCESSMESSAGE,SUCCESSCODE);
    }

    public Response saveParent(ParentInsertDto parentInsertDto){
        String encodePw = passwordEncoder.encode(parentInsertDto.getPw());
        parentInsertDto.setPw(encodePw);
        Parent parent = parentInsertDto.dtoToParent(parentInsertDto);
        Parent saveParent = parentRepo.save(parent);
        return new Response(saveParent,SUCCESSMESSAGE,SUCCESSCODE);

    }

    public Response registerUser(int userIdx, LogInDto logInDto) throws BaseException {
        Optional<User> user = userRepo.findByNickname(logInDto.getNickname());
        User user1 = user.get();
        try{
            userRepo.updateUserId(user1.getUserId(), userIdx);
            return new Response(logInDto,SUCCESSMESSAGE,SUCCESSCODE);

        }catch (Exception e){
           throw  new BaseException();
        }


    }


    public boolean checkNickname(String nickname) throws BaseException {
        try {
            return userRepo.existsByNickname(nickname);
        } catch (Exception exception) {
            throw new BaseException(UPDATEERRORMESSAGE,UPDATEERRORCODE);
        }
    }



}
