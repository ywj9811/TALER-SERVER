package com.demo.domain.responseCode;

import javax.swing.plaf.PanelUI;

public class ResponseCodeMessage {
    public static final String SUCCESSMESSAGE = "성공";
    public static final String NULLMESSAGE = "[ERROR] 들어온 값이 NULL입니다";
    public static final String USERSELECTERRORMESSAGE = "[ERROR] USER 조회에 실패했습니다";
    public static final String BOOKDETAILSSELECTERRORMESSAGE = "[ERROR] BOOKDETAILS 조회에 실패했습니다";
    public static final String BOOKROOMSELECTERRORMESSAGE = "[ERROR] BOOKROOM 조회에 실패했습니다";
    public static final String BOOKROOMINSERTERRORMESSAGE = "[ERROR] BOOKROOM 생성에 실패했습니다.";
    public static final String BOOKROOMUPDATEERRORMESSAGE = "[ERROR] BOOKROOM 업데이트에 실패했습니다.";
    public static final String BOOKROOMDELETEERRORMESSAGE = "[ERROR] BOOKROOM 삭제에 실패했습니다.";

    public static final String ROOMVIEWSELECTERRORMESSAGE = "[ERROR] ROOMVIEW 조회에 실패했습니다";

    public static final String PICTURESELECTERRORMESSAGE = "[ERROR] PICTURE 조회에 실패했습니다";
    public static final String PICTUREINSERTERRORMESSAGE = "[ERROR] PICTURE 생성에 실패했습니다";
    public static final String PICTUREUPDATEERRORMESSAGE = "[ERROR] PICTURE 업데이트에 실패했습니다";
    public static final String PICTUREDELETEERRORMESSAGE = "[ERROR] PICTURE 삭제에 실패했습니다";

    public static final String WORDSELECTERRORMESSAGE = "[ERROR] WORD 조회에 실패했습니다";
    public static final String WORDUPDATEERRORMESSAGE = "[ERROR] WORD 업데이트에 실패했습니다";
    public static final String WORDINSERTERRORMESSAGE = "[ERROR] WORD 생성에 실패했습니다";
    public static final String WORDDELETEERRORMESSAGE = "[ERROR] WORD 삭제에 실패했습니다";

    public static final String MINDMAPSELECTERRORMESSAGE = "[ERROR] MINDMAP 조회에 실패했습니다";
    public static final String MINDMAPUPDATEERRORMESSAGE = "[ERROR] MINDMAP 업데이트에 실패했습니다";
    public static final String MINDMAPINSERTERRORMESSAGE = "[ERROR] MINDMAP 생성에 실패했습니다";
    public static final String MINDMAPDELETEERRORMESSAGE = "[ERROR] MINDMAP 삭제에 실패했습니다";

    public static final String FAVORITESELECTERRORMESSAGE = "[ERROR] FAVORITE 조회에 실패했습니다";
    public static final String FRIENDSELECTERRORMESSAGE = "[ERROR] FRIEND 조회에 실패했습니다";
    public static final String USERCHARACTERSELECTERRORMESSAGE = "[ERROR] USERCHARACTER 조회에 실패했습니다";
    public static final String USERCHARACTERUPDATEERRORMESSAGE = "[ERROR] USERCHARACTER 업데이트에 실패했습니다";
    public static final String USERCHARACTERINSERTERRORMESSAGE = "[ERROR] USERCHARACTER 생성에 실패했습니다";
    public static final String USERCHARACTERDELETEERRORMESSAGE = "[ERROR] USERCHARACTER 삭제에 실패했습니다";
    public static final String BOOKROOMDUPLICATEDMESSAGE = "[ERROR] BOOKROOM 중복되었습니다";

    public static final String RECOMMENDBOOKFAVORITEDTOLISTNULLMESSAGE = "[ERROR] recommendBookFavoriteDtoList 이 비어있습니다";

    public static final String FAVORITESELECTMESSAGE = "[ERROR] FAVORITE 조회에 실패했습니다.";
    public static final String FAVORITEUPDATEMESSAGE = "[ERROR] FAVORITE 업데이트에 실패했습니다.";
    public static final String FAVORITEINSERTMESSAGE = "[ERROR] FAVORITE 생성에 실패했습니다.";
    public static final String FAVORITEDELETEMESSAGE = "[ERROR] FAVORITE 삭제에 실패했습니다.";

    public static final String FRIENDSELECTMESSAGE = "[ERROR] FRIEND 조회에 실패했습니다";
    public static final String FRIENDUPDATEMESSAGE = "[ERROR] FRIEND 업데이트에 실패했습니다";
    public static final String FRIENDINSERTMESSAGE = "[ERROR] FRIEND 생성에 실패했습니다";
    public static final String FRIENDDELETEMESSAGE = "[ERROR] FRIEND 삭제에 실패했습니다";

    public static final String EMAILERRORMESSAGE = "[ERROR] Email 전송에서 에러 발생";



    public static final String USERPASSWORDERRORMESSAGE = "[ERROR] 비밀번호가 다릅니다.";

    public static final int USERPASSWORDERRORCODE = 1000;

    public static final int SUCCESSCODE = 2000;
    public static final int NULLCODE = 3000;
    public static final int USERSELECTERRORMCODE = 4000;
    public static final int BOOKDETAILSSELECTERRORCODE = 40001;
    public static final int BOOKROOMSELECTERRORCODE = 30001;
    public static final int BOOKROOMUPDATEERRORCODE = 30002;
    public static final int BOOKROOMINSERTERRORCODE = 30003;
    public static final int BOOKROOMDELETEERRORCODE = 30003;

    public static final int PICTURESELECTERRORCODE = 60001;
    public static final int PICTUREUPDATEERRORCODE = 60002;
    public static final int PICTUREINSERTERRORCODE = 60003;
    public static final int PICTUREDELETEERRORCODE = 60004;

    public static final int WORDSELECTERRORCODE = 70001;
    public static final int WORDUPDATEERRORCODE = 70002;
    public static final int WORDINSERTERRORCODE = 70003;
    public static final int WORDDELETEERRORCODE = 70004;

    public static final int MINDMAPSELECTERRORCODE = 80001;
    public static final int MINDMAPUPDATEERRORCODE = 80002;
    public static final int MINDMAPINSERTERRORCODE = 80003;
    public static final int MINDMAPDELETEERRORCODE = 80004;
    public static final int ROOMVIEWSELECTERRORCODE = 110001;
    public static final int USERCHARACTERSELECTERRORCODE = 20001;
    public static final int USERCHARACTERSUPDATEERORCODE = 20002;
    public static final int USERCHARACTERSINSERTERRORCODE = 20003;
    public static final int USERCHARACTERDELETEERRORCODE = 20004;
    public static final int BOOKROOMDUPLICATEDCODE = 20002;

    public static final int FAVORITESELECTCODE = 100001;
    public static final int FAVORITEUPDATECODE = 100002;
    public static final int FAVORITEINSERTCODE = 100003;
    public static final int FAVORITEDELETECODE = 100004;

    public static final int FRIENDSELECTCODE = 90001;
    public static final int FRIENDUPDATECODE = 90002;
    public static final int FRIENDINSERTCODE = 90003;
    public static final int FRIENDDELETECODE = 90004;
    public static final int EMAILERRORCODE = 800;
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
