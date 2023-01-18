package com.demo.controller;

import com.demo.dto.*;
import com.demo.dto.response.BaseException;
import com.demo.dto.response.Response;
import com.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.demo.domain.*;
import com.demo.domain.Usercharacter;
import com.demo.dto.UsercharacterDto;
import com.demo.dto.UsercharacterUpdateDto;
import com.demo.service.UsercharacterService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UsercharacterService usercharacterService;
    private String role;

    //아이 회원가입
    @PostMapping("/save")
    public Response singup(@Valid @RequestBody UserInsertDto userInsertDto ) throws DuplicateMemberException {
        return userService.userSignUp(userInsertDto);
    }

    //부모 회원가입
    @PostMapping("/parent/save")
    public Response saveParent(@RequestBody ParentInsertDto parentInsertDto) throws DuplicateMemberException {
        return userService.parentSignUp(parentInsertDto);

    }

    //아이 로그인
    @PostMapping("/login")
    public Response userLogin(@RequestBody LogInDto logInDto) throws Exception {
        return userService.login(logInDto);
    }

    //부모 로그인
    @PostMapping("/parent/login")
    public Response parentLogin(@RequestBody LogInDto logInDto) throws Exception {
        return userService.login(logInDto);
    }


    @GetMapping("/takeusercharacter/{userId}/{bookId}")
    //유저 캐릭터 정보 불러오기
    public Usercharacter moveToUsercharacter(@PathVariable Long userId,@PathVariable Long bookId) {
        Usercharacter usercharacters = usercharacterService.getUsercharacter(userId,bookId);
        return usercharacters;
    }

    @PostMapping("/insertusercharacter")
    //유저 캐릭터 정보 저장하기
    public Usercharacter saveUsercharacter(UsercharacterDto usercharacterDto) {
        Usercharacter usercharacter = usercharacterService.saveUsercharacter(usercharacterDto);
        return usercharacter;
    }

    @PutMapping("/usercharacter")
    public Usercharacter updateUsercharacter(UsercharacterUpdateDto usercharacterUpdateDto) {
        Usercharacter usercharacter = usercharacterService.updateUsercharacter(usercharacterUpdateDto);
        return usercharacter;
    }





}
