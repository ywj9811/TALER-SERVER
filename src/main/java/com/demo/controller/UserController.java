package com.demo.controller;

import com.demo.dto.*;
import com.demo.dto.response.BaseException;
import com.demo.dto.response.Response;
import com.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;
import static com.demo.utils.ValidationRegex.isRegexPhonenumber;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    //아이 회원가입
    @PostMapping("singup")
    public Response singup(@RequestBody UserInsertDto userInsertDto ) throws DuplicateMemberException {
        return userService.signup(userInsertDto);
    }

    //부모 회원가입
    @PostMapping("/saveparent")
    public Response saveParent(@RequestBody ParentInsertDto parentInsertDto) throws BaseException{
        return userService.saveParent(parentInsertDto);

    }

    //아이등록
    @PostMapping("/registerUser/{userIdx}")
    public Response registerUser(@PathVariable("userIdx") int userIdx,@RequestBody LogInDto logInDto) throws BaseException {
        return userService.registerUser(userIdx,logInDto);
    }

    @PostMapping("login")
    public Response login(@RequestBody LogInDto logInDto) {
        return userService.logIn(logInDto);
    }




}
