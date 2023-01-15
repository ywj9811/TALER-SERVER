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
    public Response getMyMain(Long userId) {
        Response response = new Response();
        if (userId == null) {
            response.setMessage(NULLMESSAGE);
            response.setCode(NULLCODE);
            return response;
        }
        return mainService.getMain(userId, response);
    }

    @GetMapping("/another")
    public Response getAnotherMain(Long userId) {
        Response response = new Response();
        if (userId == null) {
            response.setMessage(NULLMESSAGE);
            response.setCode(NULLCODE);
            return response;
        }
        return mainService.getMain(userId, response);
    }
}
