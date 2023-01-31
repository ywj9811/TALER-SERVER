package com.demo.dto;

import com.demo.domain.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserSaveResponseDto {

    private String nickname;

    private String authority;

    public static UserSaveResponseDto userEntityToDto(User user) {
        if(user == null) return null;

        return UserSaveResponseDto.builder()
                .nickname(user.getNickname())
                .authority(user.getAuthority())
                .build();
    }
}
