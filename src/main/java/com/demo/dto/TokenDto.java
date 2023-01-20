package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}