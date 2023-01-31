package com.demo.dto;

import lombok.Data;

@Data
public class LogoutDto {
    String nickname;
    String jwtToken;
}
