package com.demo.service;

import com.demo.dao.BookdetailsDao;
import com.demo.dao.BookroomDao;
import com.demo.dao.FavoriteDao;
import com.demo.dao.UserDao;
import com.demo.domain.Bookroom;
import com.demo.domain.Favorite;
import com.demo.domain.Friend;
import com.demo.domain.User;
import com.demo.dto.BookRoomSelectDto;
import com.demo.dto.FavoriteInsertDto;
import com.demo.dto.RecommendFriendDto;
import com.demo.dto.response.Response;
import com.demo.repository.BookRoomRepo;
import com.demo.repository.FavoriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Book;
import java.util.*;

import static com.demo.domain.responseCode.ResponseCodeMessage.SUCCESSCODE;
import static com.demo.domain.responseCode.ResponseCodeMessage.SUCCESSMESSAGE;

@Service
public class FavoriteService {
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
    public Favorite LikeBooks(@PathVariable Long user_id, @PathVariable Long bookroom_id, @PathVariable Long book_id){
        FavoriteInsertDto favoriteInsertDto = new FavoriteInsertDto(user_id,bookroom_id,book_id,1);
        Favorite favorite = favoriteRepo.save(favoriteInsertDto.FavoriteDtoToFavorite());

        return  favorite;
    }
    //책 좋아요 취소
    public  Favorite DisLikeBooks(@PathVariable Long user_id, @PathVariable Long bookroom_id, @PathVariable Long book_id){
        FavoriteInsertDto favoriteInsertDto = new FavoriteInsertDto(user_id,bookroom_id,book_id,0);
        bookdetailsDao.setBookPopularity(book_id);
        Favorite favorite = favoriteRepo.save(favoriteInsertDto.FavoriteDtoToFavorite());

        return favorite;
    }

    public Favorite save(Long user_id, Long bookroom_id, Long book_id) {
        //책을 담을때 popularity올려야함-> dao에서 해결 -> 그린님과 공통되는 부분
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
        Optional<Bookroom> bookRoomFavoriteList = getFavoriteBookRooms(userId);

        result.put("recommendBookFavoriteDtoList", recommendBookFavoriteDtoList);
        result.put("recommendUserList", recommendUserList);
        result.put("bookRoomFavoriteList", bookRoomFavoriteList);

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

    private Optional<Bookroom> getFavoriteBookRooms(Long user_id) {
        Optional<Bookroom> getFavoriteBookRoomList;
        getFavoriteBookRoomList = bookroomRepo.findById(user_id);

        return getFavoriteBookRoomList;
    }

    public Boolean checkFavorite(Long userId, Long bookId) {
        if(favoriteDao.checkFavorite(userId,bookId))
            return true;
        return false;
    }
}
