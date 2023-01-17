package com.demo.controller;

import com.demo.dto.*;
import com.demo.dto.response.BaseException;
import com.demo.dto.response.Response;
import com.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import com.demo.domain.*;
import com.demo.domain.Usercharacter;
import com.demo.dto.UsercharacterDto;
import com.demo.dto.UsercharacterUpdateDto;
import com.demo.service.UsercharacterService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final UsercharacterService usercharacterService;

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
