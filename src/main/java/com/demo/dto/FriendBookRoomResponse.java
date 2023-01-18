package com.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendBookRoomResponse {
    private Long bookroomId;
    private Long userId;
    private Long bookId;
    private Long characterId;
    private String themeColor;
    private String themeMusicUrl;
    private String bookTitle;
    private boolean isfavorite;
    private String gender;
    private String nickname;
    private String headStyle;
    private String headColor;
    private String topStyle;
    private String topColor;
    private String pantsStyle;
    private String pantsColor;
    private String shoesStyle;
    private String shoesColor;
    private String faceColor;
    private String faceStyle;
}
