package com.demo.service;

import com.demo.domain.Bookroom;
import com.demo.domain.Friend;
import com.demo.domain.Usercharacter;
import com.demo.dto.response.Response;
import com.demo.repository.BookRoomRepo;
import com.demo.repository.FriendRepo;
import com.demo.repository.UserCharacterRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MainService {
    private final UserCharacterRepo userCharacterRepo;
    private final BookRoomRepo bookRoomRepo;
    private final FriendRepo friendRepo;

    public Response getMain(Long userId) {
        Optional<Usercharacter> optionalUsercharacter = userCharacterRepo.findByUserIdAndBookId(userId, 0L);
        if (optionalUsercharacter.isEmpty()) {
            return new Response(USERCHARACTERSELECTERRORMESSAGE, USERCHARACTERSELECTERRORCODE);
        }
        Usercharacter usercharacter = optionalUsercharacter.get();

        List<Bookroom> bookrooms = bookRoomRepo.findAllByUserId(userId);

        List<Friend> friends = friendRepo.findAllByUserId(userId);

        Map<String, Object> results = new HashMap<>();
        results.put("usercharacter", usercharacter);
        results.put("bookrooms", bookrooms);
        results.put("friends", friends);

        return new Response(results, SUCCESSMESSAGE, SUCCESSCODE);
        /**
         * 위에 : 북룸의 색상으로 쭉 나열해줌
         * -> 누르면 북룸의 정보가 보여야 함
         *
         * 중간에 : 나의 캐릭터가 나옴
         *
         * 아래 : 친구 리스트가 쭉 나옴 (색상)
         * -> 누르면 정보 나옴
         */
    }

    public Response getMainAndisFriend(Long userId, Long otherUserId) {
        Response response = getMain(otherUserId);
        Map<String, Object> result = (Map<String, Object>) response.getResult();

        List<Friend> byUserId = friendRepo.findAllByUserId(otherUserId);
        if (byUserId.contains(userId)) {
            result.put("isFollow", true);
            response.setResult(result);
            return response;
        }
        result.put("isFollow", false);
        return response;
    }
}
