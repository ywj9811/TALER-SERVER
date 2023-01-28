package com.demo.dto;

import com.demo.domain.Friend;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FriendDto {
    Long userId;
    Long userFriendId;

    public Friend toFriend() {
        return Friend.builder()
                .userId(userId)
                .userFriendId(userFriendId)
                .build();
    }
}
