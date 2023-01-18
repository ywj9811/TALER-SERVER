package com.demo.dto;

import com.demo.domain.Parent;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ParentDto {
    private String nickname;

    private String authority;

    public static ParentDto EntityToPaParentDto(Parent parent){
        return ParentDto.builder()
                .nickname(parent.getNickname())
                .authority(parent.getAuthority())
                .build();
    }
}
