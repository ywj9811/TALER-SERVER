package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendFriendDto {
    Long userId;
    String profile_color;
    String nickname;

}
