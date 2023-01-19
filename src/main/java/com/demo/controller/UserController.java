package com.demo.controller;

import com.demo.dto.*;
import com.demo.dto.response.BaseException;
import com.demo.dto.response.Response;
import com.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.bytecode.DuplicateMemberException;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.demo.domain.*;
import com.demo.domain.Usercharacter;
import com.demo.dto.EditCharacterDto;
import com.demo.dto.UsercharacterDto;
import com.demo.dto.response.Response;
import com.demo.service.EmailService;
import com.demo.service.UsercharacterService;

import javax.validation.Valid;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
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
    public Response singup(@Valid @RequestBody UserInsertDto userInsertDto ) throws DuplicateMemberException {
        return userService.userSignUp(userInsertDto);
    }

    //부모 회원가입
    @PostMapping("/parent/save")
    public Response saveParent(@RequestBody ParentInsertDto parentInsertDto) throws DuplicateMemberException {
        return userService.parentSignUp(parentInsertDto);

    }

    //아이 로그인
    @PostMapping("/login")
    public Response userLogin(@RequestBody LogInDto logInDto) throws Exception {
        return userService.login(logInDto);
    }

    //부모 로그인
    @PostMapping("/parent/login")
    public Response parentLogin(@RequestBody LogInDto logInDto) throws Exception {
        return userService.login(logInDto);
    }

    //부모 회원가입시 아이 등록을 위한 체크
    @PostMapping("/parent/check")
    public Response checkUser(@RequestBody LogInDto logInDto){
        return userService.checkUser(logInDto);
    }

    @GetMapping("/takeusercharacter/{userId}/{bookId}")
    //유저 캐릭터 정보 불러오기
    public Usercharacter moveToUsercharacter(@PathVariable Long userId,@PathVariable Long bookId) {
        Usercharacter usercharacters = usercharacterService.getUsercharacter(userId,bookId);
        return usercharacters;
    }

    @PostMapping("/character")
    //유저 캐릭터 정보 저장하기
    public Usercharacter saveUsercharacter(UsercharacterDto usercharacterDto) {
        Usercharacter usercharacter = usercharacterService.saveUsercharacter(usercharacterDto);
        return usercharacter;
    }

    @PutMapping("/character/edit/{userId}/{bookId}")
    public Response updateUsercharacter(EditCharacterDto editCharacterDto, @PathVariable String userId, @PathVariable String bookId) {
        return usercharacterService.updateUsercharacter(Long.parseLong(userId), Long.parseLong(bookId), editCharacterDto);
    }
}
/**
 * ------수정한 부분---------
 * @RequestMapping("user")로 변경 -> 그외에 회의록 : 서버 참고하여 api변경해주세요!
 *
 * updateUsercharacter()메소드 수정했습니다.
 * Response타입으로 반환하도록 만들었으며 Service타고 들어가면서 확인하시면 제가 어떻게 처리하고 있는지 확인할 수 있을 것 입니다.
 *
 * update하는 방법
 * 1. 업데이트 하고자 하는 객체(대상)를 조회하여 받아옴
 * 2. 해당 객체(엔티티)의 필드 값을 변경해 준다. (setter를 사용하면 편할 수 있지만 entity(vo, domain)에는 setter를 사용하지 않는 것을 지향하라고 합니다.
 *    그래서 setter와 비슷한 수행할 수 있도록 원하는 값을 넣어주면 해당 값으로 this.aa = aa 이런식으로 변경해주는 메소드를 생성합니다.
 * 3. 생성한 메소드를 사용하면 대상이 업데이트 됩니다! (Spring Data Jpa 내부에서 자동으로 처리)
 *
 * 그리고 UsercharacterDto 부분에서 characterId 삭제했습니다 : pk값을 받아서 수동으로 주입하는 것이 아니기 때문에
 * updateAt, createdAt과 마찬가지로 따로 만들지 않는게 좋을 듯 합니다!
 *
 * updateCharacterDto 말고 EditCharacterDto로 새로 만들었습니다!
 */
