package com.demo.service;

import com.demo.dao.BookdetailsDao;
import com.demo.dao.UserDao;
import com.demo.domain.Favorite;
import com.demo.domain.Friend;
import com.demo.domain.User;
import com.demo.dto.FavoriteInsertDto;
import com.demo.dto.RecommendFriendDto;
import com.demo.repository.FavoriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    FavoriteRepo favoriteRepo;

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
        Favorite favorite = favoriteRepo.save(favoriteInsertDto.FavoriteDtoToFavorite());

        return favorite;
    }





    public Favorite save(@PathVariable Long user_id, @PathVariable Long bookroom_id, Long book_id) {
        //책을 담을때 popularity올려야함 -> 그린님과 공통되는 부분
        FavoriteInsertDto favoriteInsertDto = new FavoriteInsertDto(user_id,bookroom_id,book_id,0);
        Favorite favorite = favoriteRepo.save(favoriteInsertDto.FavoriteDtoToFavorite());

        return favorite;
    }



    //동화책 등록시 추천 동화책
    public List<String> bookRecommendSelect(Long user_id) {
        //읽어본 동화책과 같은 장르의 동화책을 추천
        List<String> getBookGenre = bookdetailsDao.myFavoriteGenreByExperience(user_id);
        //고려해보아야 하는 점
        //안드로이드 부분에서 같은 장르의 도서를 뽑아서 선택시 테이블에 저장할것인가
        //혹은 서버쪽 테이블에서 같은 장르의 도서의 제목을 뽑아 안드로이드에 보내서 화면에 띄우게 할 것인가

        //좋아요를 눌러둔 도서를 추천

        return getBookGenre;
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

}
