package com.demo.controller;

import com.demo.domain.*;
import com.demo.domain.Usercharacter;
import com.demo.dto.UsercharacterDto;
import com.demo.dto.UsercharacterUpdateDto;
import com.demo.service.UsercharacterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/usercharacter")
public class UserController {
    private final UsercharacterService usercharacterService;
    @GetMapping("/takeusercharacter/{userId}/{bookId}")
    //유저 캐릭터 정보 불러오기
    public Usercharacter moveToUsercharacter(@PathVariable Long userId,@PathVariable Long bookId) {
        Usercharacter usercharacters = usercharacterService.getUsercharacter(userId,bookId);
        return usercharacters;
    }

    @PostMapping("/user/character")
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
