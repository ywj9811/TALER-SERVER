package com.demo.controller;

import com.demo.domain.Favorite;
import com.demo.domain.User;
import com.demo.dto.RecommendBookFavoriteDto;
import com.demo.dto.RecommendFriendDto;
import com.demo.service.BookService;
import com.demo.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InterFaceController {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    BookService bookService;

    @GetMapping("/book/recommend/{user_id}")
    //추천 동화책 방
    public List<RecommendBookFavoriteDto> bookRecommend(@PathVariable Long user_id) {
        List<RecommendBookFavoriteDto> recommendBookFavoriteDtoList = bookService.getRecommendBooks(user_id);

        return recommendBookFavoriteDtoList;
    }

    //user의 추천 친구 받기
    @GetMapping("/book/recommend/friend/{user_id}")
    public List<RecommendFriendDto> bookRecommendFriend(@PathVariable Long user_id){
        List<RecommendFriendDto> recommendUserList = new ArrayList<>();
        favoriteService.bookRecommendFriend(user_id);
        return recommendUserList;
    }

    @GetMapping("/book/readbook/{user_id}/{book_id}")
    //읽어본 동화책 클릭시 favorite table에 담기
    //초기에 읽어본 동화책 클릭시 isfavorite에는 0값 들어감
    public Favorite bookReadbook(@PathVariable Long user_id, @PathVariable Long book_id) {
        return favoriteService.save(user_id,0L,book_id);
    }

    //장르 기반 동화책 추천
    //동화책방에 들어와서 제목 클릭시 '이런 동화책은 어떤가요?'문구와 함께 보여지는 동화책
    //읽어본 동화책 중 같은 장르의 동화책 bookdetails 의 같은 genre 동화책들 추천 동화책 추천해주기
    @GetMapping("/book/recommend/select/{user_id}")
    public List<String> bookRecommendSelect(@PathVariable Long user_id) {
        return favoriteService.bookRecommendSelect(user_id);
    }



}
