package com.demo.controller;

import com.demo.domain.User;
import com.demo.dto.*;
import com.demo.service.UserService;
import com.demo.domain.config.BaseException;
import com.demo.domain.config.BaseResponse;
import static com.demo.domain.config.BaseResponseStatus.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import static com.demo.utils.ValidationRegex.isRegexPhonenumber;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
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

    @PostMapping("/LogIn")
    public BaseResponse<User> userLogIn(@RequestBody UserLoginDto userLoginDto) throws BaseException {

        try{
            User User = userService.userLogIn(userLoginDto.getNickname());
            return new BaseResponse<>(User);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
