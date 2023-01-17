package com.demo.service;

import com.demo.domain.*;
import com.demo.dto.*;
import com.demo.dto.response.Response;
import com.demo.dto.response.BaseException;
import com.demo.jwt.TokenProvider;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ParentRepo parentRepo;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public Response signup(UserInsertDto userInsertDto) throws DuplicateMemberException {
        if (userRepo.findByNickname(userInsertDto.getNickname()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }
        String encodePw = passwordEncoder.encode(userInsertDto.getPw());
        userInsertDto.setPw(encodePw);

        User user = userInsertDto.dtoToUser(userInsertDto);
        User saveuser = userRepo.save(user);
        return new Response(saveuser,SUCCESSMESSAGE,SUCCESSCODE);
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

    public Response logIn(LogInDto logInDto){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(logInDto.getNickname(), logInDto.getPw());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto tokenDto = tokenProvider.createToken(authentication);


        return new Response(tokenDto,SUCCESSMESSAGE,SUCCESSCODE);
    }



}
