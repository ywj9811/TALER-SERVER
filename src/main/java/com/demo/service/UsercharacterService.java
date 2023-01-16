package com.demo.service;

import com.demo.domain.*;
import com.demo.dto.*;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsercharacterService{

    private final UserCharacterRepo userCharacterRepo;
    public Usercharacter getUsercharacter(Long userId, Long bookId) {
        Usercharacter usercharacter = userCharacterRepo.findByUserIdAndBookId(userId, bookId).get();
        return usercharacter;
    }

    public Usercharacter saveUsercharacter(UsercharacterDto usercharacterDto){
        Usercharacter usercharacter = usercharacterDto.insertDtoToUsercharacter(usercharacterDto);
        Usercharacter save = userCharacterRepo.save(usercharacter);
        return save;
    }

    public Usercharacter updateUsercharacter(UsercharacterUpdateDto usercharacterupdateDto){
        Usercharacter usercharacter = usercharacterupdateDto.updateDtoToUsercharacter(usercharacterupdateDto);
        Usercharacter save = userCharacterRepo.save(usercharacter);
        return save;
    }

}