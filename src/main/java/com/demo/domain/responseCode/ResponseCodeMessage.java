package com.demo.domain.responseCode;

public class ResponseCodeMessage {
    public static final String SUCCESSMESSAGE = "성공";
    public static final String NULLMESSAGE = "[ERROR] 들어온 값이 NULL입니다";
    public static final String SELECTERRORMESSAGE = "[ERROR] 조회에 실패했습니다";
    public static final String UPDATEERRORMESSAGE = "[ERROR] 업데이트 실패했습니다";
    public static final String INSERTERRORMESSAGE = "[ERROR] 생성에 실패했습니다.";
    public static final int SUCCESSCODE = 2000;
    public static final int NULLCODE = 3000;
    public static final int SELECTERRORCODE = 4000;
    public static final int UPDATEERRORCODE = 5000;
    public static final int INSERTERRORCODE = 6000;
}
