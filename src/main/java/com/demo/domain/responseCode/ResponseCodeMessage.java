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
    public static final int BOOKDETAILSSELECTERRORCODE = 40001;
    public static final int BOOKROOMSELECTERRORCODE = 30001;
    public static final int ROOMVIEWSELECTERRORCODE = 110001;
    public static final int USERCHARACTERSELECTERRORCODE = 20001;
    public static final int BOOKROOMDUPLICATEDCODE = 20002;
}
/**
 * SUCCESS = 2000
 * NULL관련 에러 = 3000
 *
 * USER관련 에러 = 10000
 * USERCHARACTER관련 에러 = 20000
 * BOOKROOM관련 에러 = 30000번대
 * BOOKDETAILS 관련 에러 = 40000번대
 * PARENT 관련 에러 = 50000번대
 * PICTURE 관련 에러 = 60000번대
 * WORD 관련 에러 = 70000번대
 * MINDMAP 관련 에러 = 80000번대
 * FRIEND 관련 에러 = 90000번대
 * FAVORITE 관련 에러 = 100000번대
 * ROOMVIEW 관련 에러 = 110000번대
 *
 * SELECT - 1 (EX, 20001 : USERCHARACETER SELECT 오류)
 * UPDATE - 2
 * INSERT - 3
 * DELETE - 4
 *
 * 위 코드에 맞춰서 본인에 맞게 생성해서 사용하시면 됩니다!
 * 이외에 필요한 부분은 더 추가해서 사용!
 */
