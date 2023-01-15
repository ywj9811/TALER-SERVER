package com.demo.controller;

import com.demo.domain.Parent;
import com.demo.domain.User;
import com.demo.domain.config.BaseException;
import com.demo.domain.config.BaseResponse;
import com.demo.dto.LogInDto;
import com.demo.dto.ParentInsertDto;
import com.demo.dto.UserInsertDto;
import com.demo.dto.response.BException;
import com.demo.dto.response.Response;
import com.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.demo.domain.config.BaseResponseStatus.Post_USERS_INVALID_PHONENUMBER;
import static com.demo.utils.ValidationRegex.isRegexPhonenumber;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private  final UserService userService;

    @PostMapping("/saveuser")
    public BaseResponse<User> saveUser(@RequestBody UserInsertDto userInsertDto) throws BaseException {
        if (!isRegexPhonenumber(userInsertDto.getPhonenumber())) {
            return new BaseResponse<>(Post_USERS_INVALID_PHONENUMBER);
        }
        try{
            User user = userService.saveUser(userInsertDto);
            return new BaseResponse<>(user);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @PostMapping("/saveparent")
    public BaseResponse<Parent> saveParent(@RequestBody ParentInsertDto parentInsertDto) throws BaseException{
        Parent parent = userService.saveParent(parentInsertDto);
        return new BaseResponse<>(parent);
    }

    @PostMapping("/registerUser")
    public Response registerUser(@RequestBody LogInDto logInDto) throws BException {
        return userService.registerUser(logInDto);
    }




}
