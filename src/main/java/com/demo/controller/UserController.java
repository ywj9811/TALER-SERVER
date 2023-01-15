package com.demo.controller;

import com.demo.domain.Parent;
import com.demo.domain.User;
import com.demo.dto.LogInDto;
import com.demo.dto.ParentInsertDto;
import com.demo.dto.UserInsertDto;
import com.demo.dto.response.BaseException;
import com.demo.dto.response.Response;
import com.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.demo.domain.responseCode.ResponseCodeMessage.UPDATEERRORCODE;
import static com.demo.domain.responseCode.ResponseCodeMessage.USERSINVALIDPHONENUMBER;
import static com.demo.utils.ValidationRegex.isRegexPhonenumber;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  final UserService userService;

    //아이 회원가입
    @PostMapping("/saveuser")
    public Response saveUser(@RequestBody UserInsertDto userInsertDto) throws BaseException {
        if (!isRegexPhonenumber(userInsertDto.getPhonenumber())) {
            throw new BaseException(USERSINVALIDPHONENUMBER,UPDATEERRORCODE);
        }
        try{
            return userService.saveUser(userInsertDto);
        }catch(BaseException baseException){
            throw new BaseException();
        }


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




}
