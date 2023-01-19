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

    //아이 회원가입
    public Response userSignUp(UserInsertDto userInsertDto) throws DuplicateMemberException {
        if (userRepo.findByNickname(userInsertDto.getNickname()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }
        String encodePw = passwordEncoder.encode(userInsertDto.getPw()); //비밀번호 암호화
        userInsertDto.setPw(encodePw);

        User user = userInsertDto.dtoToUser(userInsertDto);
        UserDto userDto = UserDto.userEntityToDto(userRepo.save(user));
        return new Response(userDto,SUCCESSMESSAGE,SUCCESSCODE);
    }

    //부모 회원가입
    public Response parentSignUp(ParentInsertDto parentInsertDto) throws DuplicateMemberException {


        if (parentRepo.findByNickname(parentInsertDto.getNickname()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        String encodePw = passwordEncoder.encode(parentInsertDto.getPw()); //비밀번호 암호화
        parentInsertDto.setPw(encodePw);

        Parent parent = parentInsertDto.dtoToParent(parentInsertDto,
                userRepo.findByNickname(parentInsertDto.getUserNickname()).get().getUserId());
        ParentDto parentDto = ParentDto.EntityToPaParentDto(parentRepo.save(parent));
        return new Response(parentDto,SUCCESSMESSAGE,SUCCESSCODE);
    }

    //아이, 부모 로그인
    public Response login(LogInDto logInDto) throws Exception {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(logInDto.getNickname(), logInDto.getPw());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenDto tokenDto = tokenProvider.createToken(authentication);


        return new Response(tokenDto,SUCCESSMESSAGE,SUCCESSCODE);
    }

    //부모 회원가입시 아이 등록을 위한 체크
    public Response checkUser(LogInDto logInDto){
        Optional<User> user = userRepo.findByNickname(logInDto.getNickname());

        if(user.orElse(null) == null){
            return new Response(USERSELECTERRORMESSAGE,USERSELECTERRORMCODE);
        }
        if(parentRepo.findByUserId(user.get().getUserId()).orElse(null) != null){
            return new Response("이미 등록된 아이입니다.",USERSELECTERRORMCODE);
        }
        if(!passwordEncoder.matches(logInDto.getPw(),user.get().getPw())){
            return new Response(USERPASSWORDERRORMESSAGE,USERPASSWORDERRORCODE);
        }

        return new Response(logInDto,SUCCESSMESSAGE,SUCCESSCODE);

    }

}
