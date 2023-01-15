package com.demo.domain.responseCode;

public class ResponseCodeMessage {
    public static final String SUCCESSMESSAGE = "성공";
    public static final String NULLMESSAGE = "[ERROR] 들어온 값이 NULL입니다";
    public static final String USERSELECTERRORMESSAGE = "[ERROR] USER 조회에 실패했습니다";
    public static final String BOOKDETAILSSELECTERRORMESSAGE = "[ERROR] BOOKDETAILS 조회에 실패했습니다";
    public static final String BOOKROOMSELECTERRORMESSAGE = "[ERROR] BOOKROOM 조회에 실패했습니다";
    public static final String ROOMVIEWSELECTERRORMESSAGE = "[ERROR] ROOMVIEW 조회에 실패했습니다";
    public static final String FAVORITESELECTERRORMESSAGE = "[ERROR] FAVORITE 조회에 실패했습니다";
    public static final String FRIENDSELECTERRORMESSAGE = "[ERROR] FRIEND 조회에 실패했습니다";
    public static final String USERCHARACTERSELECTERRORMESSAGE = "[ERROR] USERCHARACTER 조회에 실패했습니다";
    public static final String UPDATEERRORMESSAGE = "[ERROR] 업데이트 실패했습니다";
    public static final String BOOKROOMDUPLICATEDMESSAGE = "[ERROR] BOOKROOM 중복되었습니다";
    public static final String USERSEXISTSNICKNAME = "이미 존재하는 아이디입니다";
    public static final String USERSINVALIDPHONENUMBER = "전화번호 형식을 확인해 주세요";
    public static final int SUCCESSCODE = 2000;
    public static final int NULLCODE = 3000;
    public static final int USERSELECTERRORMCODE = 4000;
    public static final int BOOKDETAILSSELECTERRORCODE = 4001;
    public static final int BOOKROOMSELECTERRORCODE = 4002;
    public static final int ROOMVIEWSELECTERRORCODE = 4003;
    public static final int USERCHARACTERSELECTERRORCODE = 4004;
    public static final int UPDATEERRORCODE = 5000;
    public static final int BOOKROOMDUPLICATEDCODE = 6002;
}
