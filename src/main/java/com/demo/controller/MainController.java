package com.demo.controller;

import com.demo.dto.response.Response;
import com.demo.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {
    private final MainService mainService;

    @GetMapping("/mine")
    public Response getMyMain(String userId) {
        Response response = new Response();
        try {
            if (userId == null) {
                response.setMessage(NULLMESSAGE);
                response.setCode(NULLCODE);
                return response;
            }
            return mainService.getMain(Long.parseLong(userId), response);
        } catch (Exception e) {
            response.setCode(000);
            response.setMessage("추후 작성");
            return response;
        }
    }

    @GetMapping("/another")
    public Response getAnotherMain(String userId, String otherUserId) {
        Response response = new Response();

        try {
            if (userId == null) {
                response.setMessage(NULLMESSAGE);
                response.setCode(NULLCODE);
                return response;
            }
            return mainService.getMainAndisFriend(Long.parseLong(userId), Long.parseLong(otherUserId), response);
        } catch (Exception e) {
            response.setCode(000);
            response.setMessage("추후 작성");
            return response;
        }
    }
    /**
     * 팔로우 여부 확인 필요
     */
}
