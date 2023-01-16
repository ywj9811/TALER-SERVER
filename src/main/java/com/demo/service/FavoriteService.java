package com.demo.service;

import com.demo.dao.BookdetailsDao;
import com.demo.dao.FavoriteDao;
import com.demo.dao.UserDao;
import com.demo.domain.Bookroom;
import com.demo.domain.Favorite;
import com.demo.domain.Friend;
import com.demo.domain.User;
import com.demo.dto.BookRoomSelectDto;
import com.demo.dto.FavoriteInsertDto;
import com.demo.dto.RecommendFriendDto;
import com.demo.repository.BookRoomRepo;
import com.demo.repository.FavoriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Book;
import java.util.*;

@Service
public class FavoriteService {

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

    //유저에게 추천 친구
    public List<RecommendFriendDto> bookRecommendFriend(Long user_id) {
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

    public Optional<Bookroom> getFavoriteBookRooms(Long user_id) {
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
