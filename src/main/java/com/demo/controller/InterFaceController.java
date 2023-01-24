/**
 * -----지우거나 냅두거나 상관없습니다!-----
 *
 *
 * -----Spring Data JPA 기본적인 사용법-----
 * 1. insert문 : repo.save(Entity객체) 단순하게 save로 처리할 수 있습니다! (Builder를 사용하거나 등등의 방법으로 객체 생성하여 넣어주세요)
 *
 * 2. select문 : save()와 마찬가지로 기본 제공 메소드가 있는데 findById()가 있습니다. 이것은 Pk값을 기준으로 찾는 찾아서 반환해주는 기본 메소드에요!(Optional타입으로 반환되어요)
 *      이외에 다른 조건에 의한 select를 사용하고 싶다
 *      -> findBy + 원하는 컬럼1 + AND + 원하는 컬럼1 == (SELECT * FROM ENTITY객체 WHERE 원하는 컬럼1 = ? AND 원하는 컬럼2 = ?) 이렇게 자동 생성되어 사용할 수 있습니다!
 *      findBy의 경우는 객체 하나만 조회하고 findAllBy를 사용하면 List<Entity>로 조회됩니다!
 *
 * 3. update문 : update문은 평소에 사용하던 방식과 조금 달라서 헷갈릴 수 있습니다
 *              1. findBy를 사용하여 update를 하고자 하는 대상을 가져옵니다
 *              2. 대상.set컬럼() 을 통해서 원하는 값을 넣어주면 자동으로 UPDATE가 실행됩니다 (하지만 Entity에는 setter를 사용하는 것을 지양하라 하기에... 다른 방식을 사용합시다)
 *              2-2. setter를 사용하지 않기 위해서는 해당 domain클래스 내부에 setter역할을 수행할 수 있는 메소드를 하나 생성하고 그것을 불러서 사용합니다.
 *                  ex) UserCharacter 클래스 내부에 editCharacter() 메소드를 참고하시면 됩니다 OR Bookdetails의 updatePopularity() 메소드 참고
 *
 * 4. delete문 : select문과 유사합니다.
 *               deleteById()라는 Pk값을 기준으로 삭제하는 기본 메소드를 제공하고 있으며
 *               deleteBy + 컬럼명 + AND + 컬럼명 이렇게 SELECT문과 마찬가지로 조합하여 사용할 수 있습니다.
 *               물론 전체 삭제를 위해서는 deleteAllBy 를 사용할 수 있습니다!
 *               +
 *               delete(Entity) 이렇게 객체 자체를 넣어주어 해당 객체를 삭제하는 방법도 있습니다!
 *
 * 이외에 다양한 방법이 존재하며 복잡한 쿼리의 경우 @query를 통해서 직접 쿼리문을 작성할 수 있습니다.
 * 저희가 사용한 방식은 Spring Data JPA 이며 QueryDsl을 사용하면 동적 쿼리를 좀 더 쉽게 사용할 수 있다고 합니다.
 *
 *
 * -----Response 타입 반환-----
 * 모든 컨트롤러에서 API 요청에 대한 반환은 Response로 통일하려 합니다.
 * Reponse는 dto 패키지 내부에 있으며
 *
 * Object result
 * String message
 * String code
 *
 * 이렇게 구성되어 있습니다.
 *
 * result는 Object 타입이기 때문에 모든 타입을 담을 수 있으니 기존에 반환하고 싶던 데이터를 Response.setResult()하여 담아주시면 됩니다.
 * 그 외에
 * setMessage(ResponseCodeMessage의 static타입으로 선언된 메시지)
 * setCode(ResponseCodeMessage의 static타입으로 선언된 코드)
 * 이렇게 담아서 return하시면 됩니다!
 *
 *
 *
 * -----예외처리 관련-------
 * 코드들을 살펴보면 Long타입으로 받지 않고 String으로 받도록 하는 부분들이 있습니다.
 * 이유는 파라미터로 Long타입에 문자가 들어오게 되면 예외가 발생하게 되는데 해당 예외가 스프링ExceptionHandler에서 자동으로 잡혀버려서 제가 따로 처리가 안되어 번거롭지만
 * 직접 String -> Long, Integer 등등의 과정을 거치며 예외가 발생하면 잡아주기 위해서였습니다.
 * 스프링 내장 핸들러를 중지시키면 파라미터 예외를 제 멋대로 예외 처리가 가능하다고 하지만 실패했음 + 괜히 껐다가 잘못되지 않을까 하는 두려움에 번거로운 방식으로 진행하고 있습니다!
 *
 */
package com.demo.controller;

import com.demo.domain.Favorite;
import com.demo.domain.Friend;
import com.demo.dto.response.Response;
import com.demo.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@RestController
@RequiredArgsConstructor
public class InterFaceController {

    private final FavoriteService favoriteService;

    @PostMapping("/book/favorite/{userId}/{bookId}/{bookroomId}")
    //책 좋아요 추가
    public Response addFavorite(@PathVariable String userId, @PathVariable String bookId, @PathVariable String bookroomId) {
        try {
            return favoriteService.likeBooks(Long.parseLong(userId), Long.parseLong(bookId), Long.parseLong(bookroomId));
        } catch (Exception e) {
            return new Response(FAVORITEINSERTMESSAGE,FAVORITEINSERTCODE);
        }
    }
    @DeleteMapping("book/deletefavorite/{userId}/{bookId}")
    // 책 좋아요 삭제
    public Response deleteFavorite(@PathVariable String userId, @PathVariable String bookId) {
        try {
            return favoriteService.disLikeBooks(Long.parseLong(userId), Long.parseLong(bookId));
        } catch (Exception e) {
            return new Response(FAVORITEDELETEMESSAGE, FAVORITEDELETECODE);
        }
    }
    @PostMapping("user/addfriend/{userId}/{friendUserId}")
    public Response addFriend(@PathVariable Long userId, @PathVariable Long friendUserId){
        try {
            return favoriteService.addFriend(userId, friendUserId);
        } catch (Exception e){
            return new Response(FRIENDINSERTMESSAGE, FRIENDINSERTCODE);
        }
    }

    @PostMapping("user/deletefriend/{userId}/{friendUserId}")
    public Response deleteFriend(@PathVariable Long userId, @PathVariable Long friendUserId){
        try {
            return  favoriteService.deleteFriend(userId, friendUserId);
        } catch (Exception e){
            return new Response(FRIENDDELETEMESSAGE, FAVORITEDELETECODE);
        }
    }

    @GetMapping("/book/recommend")
    //추천 동화책 방
    public Response bookRecommend(Long userId) {
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
        List<Favorite> favoriteList = new ArrayList<>();

        favoriteList.add(favoriteService.save(userId,0L,bookId1));
        favoriteList.add(favoriteService.save(userId,0L,bookId2));
        favoriteList.add(favoriteService.save(userId,0L,bookId3));
        favoriteList.add(favoriteService.save(userId,0L,bookId4));
        favoriteList.add(favoriteService.save(userId,0L,bookId5));

        return new Response(favoriteList, SUCCESSMESSAGE, SUCCESSCODE);
    }

    //동화책방에 들어와서 제목 클릭시 '이런 동화책은 어떤가요?'문구와 함께 보여지는 동화책
    //favorite에 담겨진 동화책 중 가장 선호하는 genre 2가지를 가진 book title 리스트 가져오기
    //book titles를 api검색어로 넣어 book image가져오기
    @GetMapping("/book/recommend/select")
    public Response bookRecommendSelect(@RequestParam("userId") Long userId) {
        Set<String> bookRecommendList = favoriteService.bookRecommendSelect(userId);

        return new Response(bookRecommendList, SUCCESSMESSAGE, SUCCESSCODE);
    }

    //친구가 등록한 동화책방 클릭시
    @GetMapping("/book/friend/bookroom")
    public Response bookFriendBookroom(String userId, String friendUserId, String bookId) {
        try {
            if (userId == null || friendUserId == null || bookId == null) {
                return new Response(NULLMESSAGE, NULLCODE);
            }
            //별표 확인
            return favoriteService.checkFavorite(Long.parseLong(userId), Long.parseLong(friendUserId), Long.parseLong(bookId));
            //안드쪽 별표 있게
            //안드쪽 별표 없이
        } catch (Exception e) {
            return new Response(BOOKROOMSELECTERRORMESSAGE, BOOKROOMSELECTERRORCODE);
        }
    }

    /**
     * 이 아래 추천 동화책방, 즐겨찾기 클릭시 모두 친구의 동화책방으로 이동되는 것인데, 친구 동화책방 api 그대로 사용해도 괜찮을 것 같아요
     * 단순 이동을 위한 api이니
     */

    /**
     * Response 타입으로 반환을 하고 있어서 우선 그에 맞춰서 수정했습니다
     *이 부분은 제가 더 자세히 설명 드릴게요!
     */
}
