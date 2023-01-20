package com.demo.service;

import com.demo.dao.BookdetailsDao;
import com.demo.dao.BookroomDao;
import com.demo.dao.UserDao;
import com.demo.domain.Bookroom;
import com.demo.domain.Favorite;
import com.demo.dto.BookRoomSelectDto;
import com.demo.dto.FavoriteInsertDto;
import com.demo.dto.RecommendFriendDto;
import com.demo.domain.*;
import com.demo.dto.*;
import com.demo.dto.response.Response;
import com.demo.repository.BookRoomRepo;
import com.demo.repository.FavoriteRepo;
import com.demo.repository.FriendRepo;
import lombok.RequiredArgsConstructor;
import com.demo.repository.RoomViewRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FavoriteService {
    private final RoomViewRepo roomViewRepo;
    private final BookroomDao bookroomDao;
    private final FriendRepo friendRepo;
    private final FavoriteRepo favoriteRepo;
    private final BookRoomRepo bookroomRepo;
    private final BookdetailsDao bookdetailsDao;
    private final UserDao userDao;

    //책 좋아요
    public Response likeBooks(Long userId, Long bookId, Long bookroomId){
        FavoriteInsertDto favorite = new FavoriteInsertDto(userId, bookroomId, bookId, 1);
        Favorite save = favoriteRepo.save(favorite.FavoriteDtoToFavorite());

        return new Response(save, SUCCESSMESSAGE, SUCCESSCODE);
    }
    //책 좋아요 취소
    public  Response disLikeBooks(Long userId, Long bookroomId){
        favoriteRepo.deleteByUserIdAndBookroomId(userId, bookroomId);

        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }

    public  Response addFriend(Long userId, Long friendUserId){
        friendRepo.add(userId, friendUserId);
        return new Response(SUCCESSMESSAGE,SUCCESSCODE);
    }
    public  Response deleteFriend(Long userId, Long friendUserId){
        friendRepo.delete(userId,friendUserId);
        return new Response(SUCCESSMESSAGE,SUCCESSCODE);
    }

    public Favorite save(Long user_id, Long bookroom_id, Long book_id) {
        //책을 담을때 popularity올려야함-> dao에서 해결 -> 그린님과 공통되는 부분 ?? popularity는 책방에 담을 때 올리는 것 아닌가요?!
        FavoriteInsertDto favoriteInsertDto = new FavoriteInsertDto(user_id,bookroom_id,book_id,0);
        Favorite favorite = favoriteRepo.save(favoriteInsertDto.FavoriteDtoToFavorite());

        return favorite;
    }

    //동화책 등록시 추천 동화책
    public Set<String> bookRecommendSelect(Long user_id) {
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
        resultList = bookdetailsDao.getBookTitleByBookGere(list_entries.get(0).getKey(),user_id);
        resultList.addAll(bookdetailsDao.getBookTitleByBookGere(list_entries.get(1).getKey(),user_id));
        Set<String> resultSet = new HashSet<>();
        for(String s:resultList){
            resultSet.add(s);
        }
        return resultSet;

    }

    public Response getResult(Long userId) {
        Map<String, Object> result = new HashMap<>();
        Response response = new Response();
        Set<BookRoomSelectDto> recommendBookrooms = getRecommendBooks(userId);
        Set<RecommendFriendDto> recommendUsers = bookRecommendFriend(userId);
        List<Bookroom> myFavoriteBookrooms = getFavoriteBookRooms(userId);

        if (recommendUsers == null && recommendUsers == null && myFavoriteBookrooms == null) {
            response.setCode(NULLCODE);
            response.setMessage(NULLMESSAGE);
            return response;
        } else {
            result.put("recommendUsers", recommendUsers);
            result.put("myFavoriteBookrooms", myFavoriteBookrooms);
            result.put("recommendBookrooms", recommendBookrooms);

            return new Response(result, SUCCESSMESSAGE, SUCCESSCODE);
        }
    }
    private Set<BookRoomSelectDto> getRecommendBooks(Long userId){
        //유저가 좋아요를 눌러논 동화책방의 주인이 등록한 다른 동화책방을 추천으로 주기 -> null일 경우 고려
        List<BookRoomSelectDto> bookRoomSelectDtoList = new ArrayList<>();

        if (bookroomDao.getBookroomByFavorite(userId) != null)
            bookRoomSelectDtoList.addAll(bookroomDao.getBookroomByFavorite(userId));
        if (bookroomDao.getBookroomByExperience(userId) != null)
            bookRoomSelectDtoList.addAll(bookroomDao.getBookroomByExperience(userId));

        Set<BookRoomSelectDto> bookRoomSelectDtos = new HashSet<>();

        if(bookRoomSelectDtoList == null)
            //null일경우 아무것도 보내주지 않게 하기
            return bookRoomSelectDtos;

        for (BookRoomSelectDto brs : bookRoomSelectDtoList)
            bookRoomSelectDtos.add(brs);

        return bookRoomSelectDtos;
    }
    /**
     * 이 부분에서 null인 경우에 add를 하려하면 NullPointException이 발생하길래 우선 조건문을 써서 막아뒀어요
     *
     * add할 때 add하는 값이 null이면 NPE가 발생하는 것 같아요
     * 그래서 조건문 내요잉랑 위치를 살짝 바꿨습니다!
     */

    //유저에게 추천 친구
    private Set<RecommendFriendDto> bookRecommendFriend(Long user_id) {
        List<RecommendFriendDto> recommendUsers  = new ArrayList<>();

        if (userDao.recommendFriendByFavoriteExperience(user_id) != null)
            recommendUsers = userDao.recommendFriendByFavoriteExperience(user_id);
        if (userDao.recommendFriendBySameAge(user_id) != null)
            recommendUsers.addAll(userDao.recommendFriendBySameAge(user_id));
        if (userDao.recommendFriendBySameBook(user_id) != null)
            recommendUsers.addAll(userDao.recommendFriendBySameBook(user_id));
        if (userDao.recommendFriendBySameFavoriteBook(user_id) != null)
            recommendUsers.addAll(userDao.recommendFriendBySameFavoriteBook(user_id));

        Set<RecommendFriendDto> resultSet = new HashSet<>();
        for(RecommendFriendDto recommendFriendDto:recommendUsers){
            if(recommendFriendDto.getFriendUserId() == user_id
                    || friendRepo.existsByUserFriendIdAndUserId(recommendFriendDto.getFriendUserId(), user_id))
                continue;
            log.info("추천 = {}", recommendFriendDto.getFriendUserId());
            resultSet.add(recommendFriendDto);
        }
        return resultSet;
    }
    /**
     *  friendRepo.existByUserFriendIdAndUserId(a, b)
     *  이것은 UserFriendId가 a 고 UserId가 b인 데이터가 있는가 체크하고 있으면 true, 없으면 false를 반환하는 거에요
     *  SpringDataJpa를 사용할 때 find(Select) delete 등등 뒤에 By컬럼명 하면 해당 컬럼을 기준으로 조건문을 작성해서 작동하게 되는 거에요
     */

    private List<Bookroom> getFavoriteBookRooms(Long user_id) {
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
            log.info("bookroomId = {}", bookroomId);
            Optional<Bookroom> optionalBookroom = bookroomRepo.findById(bookroomId);
            if (optionalBookroom.isPresent())
                getFavoriteBookRoomList.add(optionalBookroom.get());
        }

        return getFavoriteBookRoomList;
    }

    public Response checkFavorite(Long userId, Long friendUserId, Long bookId) {
        Roomview roomview = roomViewRepo.findByBookIdAndUserId(bookId, friendUserId);

        Boolean isFavorite = true;
        if (favoriteRepo.findByUserIdAndBookroomId(userId, roomview.getBookroomId()).isEmpty())
            isFavorite = false;

        BookRoomResponse result = new BookRoomResponse(roomview.getBookroomId(), roomview.getUserId(), roomview.getBookId(), roomview.getCharacterId(),
                roomview.getThemeColor(), roomview.getThemeMusicUrl(), roomview.getBookTitle(), isFavorite, roomview.getGender(), roomview.getNickname(),
                roomview.getHeadStyle(), roomview.getHeadColor(), roomview.getTopStyle(), roomview.getTopColor(), roomview.getPantsStyle(), roomview.getPantsColor(),
                roomview.getShoesStyle(), roomview.getShoesColor(), roomview.getFaceColor(), roomview.getFaceStyle());

        return new Response(result, SUCCESSMESSAGE, SUCCESSCODE);
    }
}

