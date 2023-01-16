package com.demo.controller;

import com.demo.domain.Favorite;
import com.demo.dto.response.Response;
import com.demo.service.BookService;
import com.demo.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@RestController
public class InterFaceController {

    @Autowired
    FavoriteService favoriteService;

    @Autowired
    BookService bookService;

    @PostMapping("/book/favorite/{user_id}/{book_id}")
    //책 좋아요 추가
    public Favorite addFavorite(@PathVariable Long user_id, @PathVariable Long book_id){
        Favorite favorite = favoriteService.LikeBooks(user_id,0l,book_id);
        return favorite;
    }
    @PostMapping("book/deletefavorite/{user_id}/{book_id}")
    // 책 좋아요 삭제
    public Favorite deleteFavorite(@PathVariable Long user_id, @PathVariable Long book_id){
        Favorite favorite = favoriteService.DisLikeBooks(user_id,0l,book_id);
        return favorite;
    }
//    @PostMapping("user/addfriend/{user_id}")
//    public Friend addFriend(@PathVariable Long user_id){
//        Friend friend = favoriteService.
//    }
//
//
//    @PostMapping("user/deletefriend/{user_id}")

    @GetMapping("/book/recommend")
    //추천 동화책 방
    public Response bookRecommend(Long userId) {
//        List<BookRoomSelectDto> recommendBookFavoriteDtoList = bookService.getRecommendBooks(userId);
//
//        //user의 추천 친구
//        List<RecommendFriendDto> recommendUserList = favoriteService.bookRecommendFriend(userId);
//        //model.setAttribute("recommendUserList",recommendUserList);
//
//        //별표 누른 동화책방 리스트 가져오기
//        Optional<Bookroom> bookRoomFavoriteList = favoriteService.getFavoriteBookRooms(userId);
//        //model.setAttribute("bookRoomFavoriteList",bookRoomFavoriteList);

        return favoriteService.getResult(userId);
    }

    /**
     * 기존에 서비스에서 메소드 하나하나 불러서 처리하는 부분
     * 서비스 내부에서 한번에 처리하도록 수정했습니다!
     * 같은 파라미터를 넘기기에 한번의 서비스 호출로 처리할 수 있을 듯 하여 getResult 내부에서 처리하도록 수정했습니다.
     * 추가 주석도 서비스 내부에 작성했으니 참고해주세요! (내부에서 Response 타입으로 반환하고 있습니다)
     * Response 반환은 이 /book/recommend API 내용을 참고해서 살펴보시면 좋을 듯 합니다! (아직 예외처리는 안함)
     * 우선 승지님 나머지 API도 모두 Response타입으로 반환하고 있는데 아직은 미흡하게 리턴하고 있으니 리팩토링 하면서 다시 잡아봐요
     */

    /**
     * userId와 user_id가 파라미터에서 혼용되고 있길래 이런 부분은 모두 userId, bookId 등등의 모양으로 수정했습니다!
     * 앞으로 Id같은 모습으로 작성해주세요!
     * 이외에 몇가지 @PathVaribale 을 @RequestParam으로 수정했습니다!
     *
     * 수정된 부분과 고쳐야할 부분들 한번 쭉 보시고 같이 해봅시다!!
     */

    @PostMapping("/book/readbook")
    //읽어본 동화책 클릭시 favorite table에 담기
    //초기에 읽어본 동화책 클릭시 isfavorite에는 0값 들어감
    //해당 book_id favorite의 popularity 올려야함
    public Response bookReadbook(@RequestParam("userId") Long userId, @RequestParam("bookId1") Long bookId1, @RequestParam("bookId2") Long bookId2, @RequestParam("bookId3") Long bookId3, @RequestParam("bookId4")Long bookId4, @RequestParam("bookId5") Long bookId5) {
        Response response = new Response();
        List<Favorite> favoriteList = new ArrayList<>();

        favoriteList.add(favoriteService.save(userId,0L,bookId1));
        favoriteList.add(favoriteService.save(userId,0L,bookId2));
        favoriteList.add(favoriteService.save(userId,0L,bookId3));
        favoriteList.add(favoriteService.save(userId,0L,bookId4));
        favoriteList.add(favoriteService.save(userId,0L,bookId5));

        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);
        response.setResult(favoriteList);

        return response;
    }

    //동화책방에 들어와서 제목 클릭시 '이런 동화책은 어떤가요?'문구와 함께 보여지는 동화책
    //favorite에 담겨진 동화책 중 가장 선호하는 genre 2가지를 가진 book title 리스트 가져오기
    //book titles를 api검색어로 넣어 book image가져오기
    @GetMapping("/book/recommend/select")
    public Response bookRecommendSelect(@RequestParam("userId") Long userId) {
        Response response = new Response();

        List<String> bookRecommendList = favoriteService.bookRecommendSelect(userId);

        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);
        response.setResult(bookRecommendList);

        return response;
    }

    //추천 친구 클릭시 로드 되는 메인페이지
//    @GetMapping("/main/friend")
//    public List bookRecommendSelect(@RequestParam("user_id") Long user_id, @RequestParam("friend_id")) {
//        return favoriteService.bookRecommendSelect(user_id);
//    }

    //이미 follow한 친구 클릭시 로드 되는 메인페이지

    //친구가 등록한 동화책방 클릭시
    @GetMapping("/book/friend/bookroom")
    public Response bookFriendBookroom(Long notFriendUserId, Long bookId) {
        Response response = new Response();
        //별표 확인
        if(favoriteService.checkFavorite(notFriendUserId, bookId)){
            //안드쪽 별표 있게
        }else{
            //안드쪽 별표 없이
        }
        return bookService.selectBookRoom(bookId, notFriendUserId, response);
    }

    //추천 동화책방 클릭시 로드되는 동화책방
    @GetMapping("/book/recommend/bookroom")
    public Response bookRecommendBookroom(Long notFriendUserId, Long bookId) {
        Response response = new Response();
        //안드로이드 측 별표 없이
        return bookService.selectBookRoom(bookId,notFriendUserId, response);
    }

    //즐겨찾기 클릭시 로드 되는 동화책방
    @GetMapping("/book/favorite/bookroom")
    public Response bookFavoriteBookroom(Long notFriendUserId, Long bookId) {
        Response response = new Response();
        //안드로이드 측 별표 있게
        return bookService.selectBookRoom(bookId,notFriendUserId, response);
    }

    /**
     * Response 타입으로 반환을 하고 있어서 우선 그에 맞춰서 수정했습니다
     *이 부분은 제가 더 자세히 설명 드릴게요!
     */
}
