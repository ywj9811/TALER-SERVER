package com.demo.dto;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}