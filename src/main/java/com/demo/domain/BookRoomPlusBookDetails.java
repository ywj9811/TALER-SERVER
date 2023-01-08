package com.demo.domain;

public interface BookRoomPlusBookDetails {
    /**
     * 결과값이 안나옴 null로 나옴
     * 나중에 이유를 알게 되면 고치도록 하자.
     * 우선 작동하는 방식으로 하자.
     */
    Long getBookId();
    String getBookTitle();
    String getBookAuthor();
    String getBookGenre();
    String getBookPopularity();

    Long getBookroomId();
    Long getUserId();
    Long getCharacterId();
    String getThemeColor();
    String getThemeMusicUrl();

//    Bookroom getBookroom();
//    Bookdetails getBookdetails();
}
