package com.demo.service;

import com.demo.dao.BookdetailsDao;
import com.demo.dao.BookroomDao;
import com.demo.dao.FavoriteDao;
import com.demo.dao.UserDao;
import com.demo.domain.*;
import com.demo.dto.BookRoomSelectDto;
import com.demo.dto.FavoriteInsertDto;
import com.demo.dto.FriendBookRoomResponse;
import com.demo.dto.RecommendFriendDto;
import com.demo.dto.response.Response;
import com.demo.repository.BookRoomRepo;
import com.demo.repository.FavoriteRepo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Book;
import java.util.*;

import static com.demo.domain.responseCode.ResponseCodeMessage.SUCCESSCODE;
import static com.demo.domain.responseCode.ResponseCodeMessage.SUCCESSMESSAGE;

@Service
public class FavoriteService {
    @Autowired
    BookService bookService;
    @Autowired
    BookroomDao bookroomDao;

    @Autowired
    FavoriteRepo favoriteRepo;

    @Autowired
    BookRoomRepo bookroomRepo;

    @Autowired
    FavoriteDao favoriteDao;

    @Autowired
    BookdetailsDao bookdetailsDao;

    @Autowired
    UserDao userDao;

    //책 좋아요
    public Favorite likeBooks(Long userId, Long bookId){
        Favorite favorite = favoriteRepo.Like(userId,bookId);

        return  favorite;
    }
    //책 좋아요 취소
    public  Favorite disLikeBooks(Long userId, Long bookId){
        Favorite favorite = favoriteRepo.DisLike(userId,bookId);

        return favorite;
    }

    public Favorite save(Long user_id, Long bookroom_id, Long book_id) {
        //책을 담을때 popularity올려야함-> dao에서 해결 -> 그린님과 공통되는 부분 ?? popularity는 책방에 담을 때 올리는 것 아닌가요?!
        FavoriteInsertDto favoriteInsertDto = new FavoriteInsertDto(user_id,bookroom_id,book_id,0);
        Favorite favorite = favoriteRepo.save(favoriteInsertDto.FavoriteDtoToFavorite());

        return favorite;
    }

    //동화책 등록시 추천 동화책
    public List<String> bookRecommendSelect(Long user_id) {
        //읽어본 동화책, 좋아요를 눌러둔 동화책의 같은 장르의 동화책을 추천
        List<String> getBookGenre = bookdetailsDao.myFavoriteGenreByExperience(user_id);

        //해당 장르 중 가장 많이 나온 장르를 뽑기
        Map<String, Integer> genreCount = new HashMap<>();
        for(String genreStr:getBookGenre){
            if(!genreCount.containsKey(genreStr))
                genreCount.put(genreStr,0);
            genreCount.put(genreStr,genreCount.get(genreStr)+1);
        }
        List<Map.Entry<String, Integer>> list_entries = new ArrayList<Map.Entry<String, Integer>>(genreCount.entrySet());

        // 비교함수 Comparator를 사용하여 내림차순 정렬
        Collections.sort(list_entries, new Comparator<Map.Entry<String, Integer>>() {
            // compare로 값을 비교
            public int compare(Map.Entry<String, Integer> obj1, Map.Entry<String, Integer> obj2) {
                // 오름 차순 정렬
                return obj2.getValue().compareTo(obj1.getValue());
            }
        });

        List<String> resultList = new ArrayList<>();
        resultList.addAll(bookdetailsDao.getBookTitleByBookGere(list_entries.get(0).getKey(),user_id));
        resultList.addAll(bookdetailsDao.getBookTitleByBookGere(list_entries.get(1).getKey(),user_id));
        //사용자가 좋아하는 장르 기반 다른 동화책 추천
        //고려해야하는 점: 사용자가 이미 가지고 있는 동화책은 추천해주면 안됨
        return resultList;
    }

    public Response getResult(Long userId) {
        Map<String, Object> result = new HashMap<>();
        Response response = new Response();

        List<BookRoomSelectDto> recommendBookFavoriteDtoList = getRecommendBooks(userId);
        List<RecommendFriendDto> recommendUserList = bookRecommendFriend(userId);
        List<Bookroom> bookRoomFavoriteList = getFavoriteBookRooms(userId);

        result.put("recommendBookFavoriteDtoList", recommendBookFavoriteDtoList);
        result.put("recommendUserList", recommendUserList);
        result.put("bookRoomFavoriteList", bookRoomFavoriteList);
        //별표 누른 동화책 가져와

        response.setResult(result);
        response.setCode(SUCCESSCODE);
        response.setMessage(SUCCESSMESSAGE);

        return response;
    }

    /**
     * getRecommendBooks()
     * bookRecommendFriend()
     * getFavoriteBookRooms()
     * 모두 같은 파라미터를 받는데 컨트롤러에서 각각 호출하고 있길래 서비스 내부에서 모두 처리할 수 있도록 합쳤습니다.
     * 그리고 BookService -> favoriteService로 getRecommendBooks 그냥 가져왔어요..ㅎ
     *
     * 그리고 위와 같이 map을 만들어서 key에 리스트 이름, value에 해당 리스트 넣었습니다!
     * 그리고 Response 모습같은 경우 제가 우선 만들어 두었는데 한번 이해해보고 헷갈리는 부분은 물어보세요!
     */

    private List<BookRoomSelectDto> getRecommendBooks(Long id){
        //유저가 좋아요를 눌러논 동화책방의 주인이 등록한 다른 동화책방을 추천으로 주기 -> null일 경우 고려
        List<BookRoomSelectDto> bookRoomSelectDtoList = new ArrayList<>();
        if (bookroomDao.getBookroomByFavorite(id) != null) {
            bookRoomSelectDtoList.addAll(bookroomDao.getBookroomByFavorite(id));
        }
        System.out.println(bookRoomSelectDtoList);
        //유저가 이전에 읽어본 동화책을 등록한 동화책방을 추천
        if (bookroomDao.getBookroomByExperience(id) != null) {
            bookRoomSelectDtoList.addAll(bookroomDao.getBookroomByExperience(id));
        }
        return bookRoomSelectDtoList;
    }
    /**
     * 이 부분에서 null인 경우에 add를 하려하면 NullPointException이 발생하길래 우선 조건문을 써서 막아뒀어요
     */

    //유저에게 추천 친구
    private List<RecommendFriendDto> bookRecommendFriend(Long user_id) {
        List<RecommendFriendDto> recommendUserList  = new ArrayList<>();
        //과거 읽어본 동화책을 등록한 다른 user list
        recommendUserList = userDao.recommendFriendByFavoriteExperience(user_id);
        //나이가 같거나 등록한 동화책이 같은 경우가 2개 이상인 경우
        recommendUserList.addAll(userDao.recommendFriendBySameAge(user_id));
        recommendUserList.addAll(userDao.recommendFriendBySameBook(user_id));
        //좋아요를 눌러둔 동화책이 3개 이상인 경우
        recommendUserList.addAll(userDao.recommendFriendBySameFavoriteBook(user_id));
        //겹치는 친구가 2명 이상인 경우

        //->이 중 제일 많이 겹치는 user를 리스트에서 뽑아 추천하기
        return recommendUserList;
    }

    /**
     * 여기도 위와 같이 null일 경우 오류가 발생할 것 같기도 한데, null이 안나와서..
     * 나중에 필요하면 처리!
     */

    private List<Bookroom> getFavoriteBookRooms(Long user_id) {
//        Optional<Bookroom> getFavoriteBookRoomList;
//        getFavoriteBookRoomList = bookroomRepo.findById(user_id);
        List<Bookroom> getFavoriteBookRoomList = new ArrayList<>();
        /**
         * 이건 그냥 본인의 책방 리스트를 가져오는 중입니다 -> 좋아요 누른 책방이 아님
         * 그리고 findById는 해당 pk값으로 가져오는 것입니다.
         * userId를 사용하기 위해서는 findByUserId라는 메소드를 만들고 사용하면 됩니다.
         *
         * 우선 제 나름대로 수정했습니다.
         * favorite에서 bookroomId를 가져와서 그 bookroomId를 통해 bookroom조회하여 리스트로 만들어 반환합니다.
         */
        List<Long> bookroomIds = favoriteRepo.findBookroomIdByUserId(user_id);
        for (Long bookroomId : bookroomIds) {
            Optional<Bookroom> byId = bookroomRepo.findById(bookroomId);
            if (byId.isPresent())
                getFavoriteBookRoomList.add(byId.get());
        }

        return getFavoriteBookRoomList;
    }

    public Response checkFavorite(Long userId, Long friendUserId, Long bookId, Response response) {
        Roomview roomview = (Roomview) bookService.selectBookRoom(bookId, friendUserId, response).getResult();

        Boolean isFavorite = true;
        if (favoriteRepo.findByUserIdAndBookroomId(userId, roomview.getBookroomId()).isEmpty())
            isFavorite = false;
        FriendBookRoomResponse result = new FriendBookRoomResponse(roomview.getBookroomId(), roomview.getUserId(), roomview.getBookId(), roomview.getCharacterId(),
                roomview.getThemeColor(), roomview.getThemeMusicUrl(), roomview.getBookTitle(), isFavorite, roomview.getGender(), roomview.getNickname(),
                roomview.getHeadStyle(), roomview.getHeadColor(), roomview.getTopStyle(), roomview.getTopColor(), roomview.getPantsStyle(), roomview.getPantsColor(),
                roomview.getShoesStyle(), roomview.getShoesColor(), roomview.getFaceColor(), roomview.getFaceStyle());

        response.setResult(result);
        response.setCode(SUCCESSCODE);
        response.setMessage(SUCCESSMESSAGE);

        return response;
    }
}

