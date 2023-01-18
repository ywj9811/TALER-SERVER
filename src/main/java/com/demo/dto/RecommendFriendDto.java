package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RecommendFriendDto {
    Long FriendUserId;
    String profile_color;
    String nickname;

}
