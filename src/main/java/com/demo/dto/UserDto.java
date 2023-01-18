package com.demo.dto;

import com.demo.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    private Long userId;

    private String nickname;

    private String authority;

    public static UserDto userEntityToDto(User user) {
        if(user == null) return null;

        return UserDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .authority(user.getAuthority())
                .build();
    }
}
