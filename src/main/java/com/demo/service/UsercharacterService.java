package com.demo.service;

import com.demo.domain.*;
import com.demo.domain.responseCode.ResponseCodeMessage;
import com.demo.dto.*;
import com.demo.dto.response.Response;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UsercharacterService{

    private final UserCharacterRepo userCharacterRepo;
    public Response getUsercharacter(Long userId, Long bookId) {
        Usercharacter usercharacter = userCharacterRepo.findByUserIdAndBookId(userId, bookId).get();

        return new Response(usercharacter, SUCCESSMESSAGE, SUCCESSCODE);
    }
    //.get()을 사용해서 Optional에서 따로 받아왔습니다.
    //Optional을 사용하면 .empty가 가능해서 예외처리가 쉬워 사용했습니다

    public Response saveUsercharacter(UsercharacterDto usercharacterDto){
        Usercharacter usercharacter = usercharacterDto.insertDtoToUsercharacter(usercharacterDto);
        Usercharacter save = userCharacterRepo.save(usercharacter);
        return new Response(save, SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response updateUsercharacter(Long userId, Long bookId, EditCharacterDto editCharacterDto){
        Optional<Usercharacter> optionalUsercharacter = userCharacterRepo.findByUserIdAndBookId(userId, bookId);
        if (optionalUsercharacter.isEmpty())
            return new Response(USERCHARACTERSELECTERRORMESSAGE, USERCHARACTERSELECTERRORCODE);
        Usercharacter usercharacter = optionalUsercharacter.get();

        usercharacter.editCharacter(editCharacterDto);
        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }

}