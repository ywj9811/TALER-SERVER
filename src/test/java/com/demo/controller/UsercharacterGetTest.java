package com.demo.controller;

import com.demo.domain.*;
import com.demo.service.UsercharacterService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Transactional
class UsercharacterGetTest {
    @Autowired
    UsercharacterService usercharacterService;

    @Test
    void usercharacterSucess(){
        Usercharacter usercharacter = (Usercharacter) usercharacterService.getUsercharacter(24L,6L);
        log.info("characterId = {}",usercharacter.getCharacterId());
        log.info("bookId = {}",usercharacter.getBookId());
        log.info("createdAt={}",usercharacter.getCreatedAt());
        log.info("faceColor={}",usercharacter.getFaceColor());
        log.info("faceStyle={}",usercharacter.getFaceStyle());
        log.info("gender={}",usercharacter.getGender());
        log.info("headColor={}",usercharacter.getHeadColor());
        log.info("headStyle={}",usercharacter.getHeadColor());
        log.info("nickname={}",usercharacter.getNickname());
    }

    @Test
    void loadUserCharacter(){
        Usercharacter usercharacter = (Usercharacter) usercharacterService.getUsercharacter(25L,6L);
        assertThat(usercharacter).isNull();
    }

}