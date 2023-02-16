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

import com.demo.dto.*;
import com.demo.dto.response.Response;
import com.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import com.demo.service.EmailService;
import com.demo.service.UsercharacterService;

import javax.validation.Valid;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;
    private final UsercharacterService usercharacterService;
    private final EmailService emailService;

    @PostMapping("/emailConfirm")
    public Response emailConfirm(@RequestParam String email) throws Exception {
        try {
            return emailService.sendSimpleMessage(email);
        } catch (IllegalArgumentException e) {
            return new Response(EMAILERRORMESSAGE, EMAILERRORCODE);
        }
    }

    //아이 회원가입
    @PostMapping("/save")
    public Response singup(@Valid @RequestBody UserInsertDto userInsertDto ){
        try{
            return userService.userSignUp(userInsertDto);
        }catch(DuplicateMemberException e){
            return new Response(DUPLICATEUSERMESSAGE, DUPLICATEUSERCODE);
        }
    }

    //부모 회원가입
    @PostMapping("/parent/save")
    public Response saveParent(@RequestBody ParentInsertDto parentInsertDto){
        try{
            return userService.parentSignUp(parentInsertDto);
        }catch(DuplicateMemberException e){
            return new Response(DUPLICATEUSERMESSAGE, DUPLICATEUSERCODE);
        }
    }

    //아이 로그인
    @PostMapping("/login")
    public Response userLogin(@RequestBody LogInDto logInDto) {
        try{
            return userService.login(logInDto);
        }catch(BadCredentialsException e){
            e.printStackTrace();
            return new Response(USERIDPASSWRODERRORMESSAGE,USERIDPASSWRODERRORCODE);
        }catch(Exception e){
            e.printStackTrace();
            return new Response(USERLOGINERRORMESSAGE,USERLOGINERRORCODE);
        }

    }

    //부모 로그인
    @PostMapping("/parent/login")
    public Response parentLogin(@RequestBody LogInDto logInDto) {
        try{
            return userService.login(logInDto);
        }catch(BadCredentialsException e){
            return new Response(USERIDPASSWRODERRORMESSAGE,USERIDPASSWRODERRORCODE);
        }catch(Exception e){
            return new Response(USERLOGINERRORMESSAGE,USERLOGINERRORCODE);
        }
    }


    //부모 회원가입시 아이 등록을 위한 체크
    @PostMapping("/parent/check")
    public Response checkUser(@RequestBody LogInDto logInDto){
        return userService.checkUser(logInDto);
    }



    @PostMapping("/reIssueAccessToken/{nickname}")
    public Response reIssueAccessToken(@PathVariable String nickname){
        return userService.reIssueAccessToken(nickname);
    }

    @PostMapping("/logout")
    public Response logout(@RequestBody LogoutDto logoutDto){
        return userService.logout(logoutDto);
    }


}
