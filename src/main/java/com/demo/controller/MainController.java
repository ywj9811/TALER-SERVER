package com.demo.controller;

import com.demo.dto.response.Response;
import com.demo.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private final MainService mainService;

    @GetMapping("/mine")
    public Response getMyMain(String userId) {
        try {
            if (userId == null) {
                return new Response(NULLMESSAGE, NULLCODE);
            }
            return mainService.getMain(Long.parseLong(userId));
        } catch (Exception e) {
            return new Response();
        }
    }

    //추천 친구 클릭시 로드 되는 메인페이지
//    @GetMapping("/main/friend")
//    public List bookRecommendSelect(@RequestParam("user_id") Long user_id, @RequestParam("friend_id")) {
//        return favoriteService.bookRecommendSelect(user_id);
//    }

    //이미 follow한 친구 클릭시 로드 되는 메인페이지


    @GetMapping("/another")
    public Response getAnotherMain(String userId, String otherUserId) {
        try {
            if (userId == null) {
                return new Response(NULLMESSAGE, NULLCODE);
            }
            return mainService.getMainAndisFriend(Long.parseLong(userId), Long.parseLong(otherUserId));
        } catch (Exception e) {
            return new Response();
        }
    }
    /**
     * 팔로우 여부 확인 필요
     */
}
