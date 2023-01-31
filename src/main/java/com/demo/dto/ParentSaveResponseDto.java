package com.demo.dto;

import com.demo.domain.Parent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParentSaveResponseDto {
    private String nickname;

    private String authority;

    public static ParentSaveResponseDto EntityToPaParentDto(Parent parent){
        return ParentSaveResponseDto.builder()
                .nickname(parent.getNickname())
                .authority(parent.getAuthority())
                .build();
    }
}
