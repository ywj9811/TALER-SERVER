package com.demo.service;

import com.demo.domain.*;
import com.demo.dto.*;
import com.demo.dto.MindInsertDto;
import com.demo.dto.PictureInsertDto;
import com.demo.dto.WordInsertDto;
import com.demo.dto.response.Response;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.demo.domain.responseCode.ResponseCodeMessage.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookService {
    private final BookRoomRepo bookRoomRepo;
    private final BookDetailsRepo bookDetailsRepo;
    private final FavoriteRepo favoriteRepo;
    private final RoomViewRepo roomViewRepo;
    private final PictureRepo pictureRepo;
    private final WordRepo wordRepo;
    private final MindMapRepo mindMapRepo;
    private final UserCharacterRepo userCharacterRepo;

    /**
     * View를 이용하는 방식으로 작성함
     * join에서 문제 발생 -> 하나하나 조회는 비효율 -> view 사용
     */
    public Response selectBookRoom(Long bookId, Long userId, Response response) {
        boolean isFavorite = true;

        Roomview roomview = roomViewRepo.findByBookIdAndUserId(bookId, userId);
        if (roomview == null) {
            response.setMessage(ROOMVIEWSELECTERRORMESSAGE);
            response.setCode(ROOMVIEWSELECTERRORCODE);
            return response;
        }

        if (favoriteRepo.findByUserIdAndBookroomId(userId, roomview.getBookroomId()).isEmpty())
            isFavorite = false;

        BookRoomResponse result = new BookRoomResponse(roomview.getBookroomId(), roomview.getUserId(), roomview.getBookId(), roomview.getCharacterId(),
                roomview.getThemeColor(), roomview.getThemeMusicUrl(), roomview.getBookTitle(), isFavorite, roomview.getGender(), roomview.getNickname(),
                roomview.getHeadStyle(), roomview.getHeadColor(), roomview.getTopStyle(), roomview.getTopColor(), roomview.getPantsStyle(), roomview.getPantsColor(),
                roomview.getShoesStyle(), roomview.getShoesColor(), roomview.getFaceColor(), roomview.getFaceStyle());

        response.setResult(result);
        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);
        return response;
    }

    public Response saveBookRoom(BookRoomInsertDto bookRoomInsertDto, Response response) {
        //중복되는 북룸을 생성하는지 체크
        Long bookId = bookRoomInsertDto.getBookId();
        List<Long> bookIds = bookRoomRepo.findBookroomId(bookRoomInsertDto.getUserId());
        if (bookIds.contains(bookId)) {
            response.setMessage(BOOKROOMDUPLICATEDMESSAGE);
            response.setCode(BOOKROOMDUPLICATEDCODE);
            return response;
        }

        //bookroom을 생성하면서 bookdetails에서 popularity를 +1함
        //만약 책이 조회가 안된다면 예외 반환
        if (updateBookPopularity(bookRoomInsertDto) == null) {
            response.setMessage(BOOKDETAILSSELECTERRORMESSAGE);
            response.setCode(BOOKDETAILSSELECTERRORCODE);
            return response;
        }

        //기본 캐릭터를 바탕으로 bookroom기본 캐릭터 생성
        //만약 기존 캐릭터 조회가 안된다면 예외반환
        if (insertDefaultCharacter(bookRoomInsertDto) == null) {
            response.setMessage(USERCHARACTERSELECTERRORMESSAGE);
            response.setCode(USERCHARACTERSELECTERRORCODE);
            return response;
        }

        Bookroom bookroom = bookRoomInsertDto.dtoToBookRoom(bookRoomInsertDto);
        Bookroom save = bookRoomRepo.save(bookroom);
        log.info("bookroom save = {}", save);
        //생성

        //성공
        response.setResult(bookroom);
        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);

        return response;
    }

    //book detail에 검색한 동화책 파싱하는 작업도 해야할 것 같습니다..! book details에서 값을 찾아서 popularity를 올리는 경우는
    //검색한 도서가 이미 book details테이블에 있는 경우이고
    //검색 도서가 book details에 없다면 테이블에 값을 넣고 popularity를 0으로 세팅 해야할 것 같아요! -> 안드로이드 파트와 이야기 후 결정
    //popularity 업데이트 시키는 메소드
    private Bookdetails updateBookPopularity(BookRoomInsertDto bookRoomInsertDto) {
        Optional<Bookdetails> optionalBookdetails = bookDetailsRepo.findById(bookRoomInsertDto.getBookId());
        if (optionalBookdetails.isEmpty()) {
            return null;
        }
        Bookdetails bookdetails = optionalBookdetails.get();
        bookdetails.updatePopularity();
        log.info("bookdetails update 실행");
        return bookdetails;
    }

    //기본 케릭터 가져와서 bookroom생성시 등록하는 메소드
    private Usercharacter insertDefaultCharacter(BookRoomInsertDto bookRoomInsertDto) {
        Optional<Usercharacter> optionalUsercharacter = userCharacterRepo.findByUserIdAndBookId(bookRoomInsertDto.getUserId(), 0L);
        if (optionalUsercharacter.isEmpty()) {
            return null;
        }
        Usercharacter defaultCharacter = optionalUsercharacter.get();
        Usercharacter usercharacter = DefaultCharacterDto.dtoToEntity(defaultCharacter, bookRoomInsertDto.getBookId());

        Usercharacter characterSave = userCharacterRepo.save(usercharacter);
        log.info("default character save = {}", characterSave);
        return characterSave;
    }

    //bookroom 생성시 isfavorite를 0으로하여 등록하는 메소드
    //!!!삭제함!!!

    //themeColor 추가용(업데이트)
    public Response updateThemeColor(String themeColor, Long bookroomId, Response response) {
        Bookroom bookroom = getBookroom(bookroomId);
        bookroom.updateThemeColor(themeColor);
        log.info("update themeColor = {}", themeColor);

        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);
        return response;
    }

    //themeMusicUrl 추가용(업데이트)
    public Response updateThemeMusicUrl(String themeMusicUrl, Long bookroomId, Response response) {
        Bookroom bookroom = getBookroom(bookroomId);
        bookroom.updateThemeMusicUrl(themeMusicUrl);
        log.info("update themeMusicUrl = {}", themeMusicUrl);

        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);
        return response;
    }

    //bookroom정보 가져오기
    private Bookroom getBookroom(Long bookroomId) {
        Optional<Bookroom> optionalBookroom = bookRoomRepo.findById(bookroomId);
        Bookroom bookroom = optionalBookroom.get();
        return bookroom;
    }

    public Response deleteBookRoom(Long bookroomId, Response response) {
        Bookroom bookroom = getBookroom(bookroomId);
        bookRoomRepo.delete(bookroom);
        pictureRepo.deleteAllByBookroomId(bookroomId);
        wordRepo.deleteAllByBookroomId(bookroomId);
        mindMapRepo.deleteAllByBookroomId(bookroomId);
        userCharacterRepo.deleteByUserIdAndBookId(bookroom.getUserId(), bookroom.getBookId());
        favoriteRepo.deleteAllByBookroomId(bookroomId);

        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);
        return response;
    }

    public Response getPictureByBookroomId(Long bookroomId, Response response) {
        List<Picturetable> picturetables = pictureRepo.findAllByBookroomId(bookroomId);
        response.setCode(SUCCESSCODE);
        response.setMessage(SUCCESSMESSAGE);
        response.setResult(picturetables);
        return response;
    }
    public Response savePicture(PictureInsertDto pictureInsertDto, Response response) {
        Picturetable picturetable = pictureInsertDto.insertDtoToPicturetable(pictureInsertDto);
        Picturetable save = pictureRepo.save(picturetable);
        log.info("picture save = {}", save);
        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);
        response.setResult(save);

        return response;
    }

    public Response getWordByroomId(Long bookroomId, Response response) {
        List<Wordtable> wordtables = wordRepo.findAllByBookroomId(bookroomId);
        response.setResult(wordtables);
        response.setCode(SUCCESSCODE);
        response.setMessage(SUCCESSMESSAGE);
        return response;
    }
    public Response saveWord(WordInsertDto wordInsertDto, Response response) {
        Wordtable wordtable = wordInsertDto.insertDtoToWordtable(wordInsertDto);
        Wordtable save = wordRepo.save(wordtable);
        log.info("word save = {}", save);

        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);
        response.setResult(save);
        return response;
    }

    public Response getMindmapByBookroomId(Long bookroomId, Response response) {
        List<Mindmap> mindmaps = mindMapRepo.findAllByBookroomId(bookroomId);
        response.setResult(mindmaps);
        response.setCode(SUCCESSCODE);
        response.setMessage(SUCCESSMESSAGE);
        return response;
    }
    public Response saveMind(MindInsertDto mindInsertDto, Response response) {
        Mindmap mindmap = mindInsertDto.insertDtoToMindmap(mindInsertDto);
        Mindmap save = mindMapRepo.save(mindmap);
        log.info("mind save = {}", save);

        response.setMessage(SUCCESSMESSAGE);
        response.setCode(SUCCESSCODE);
        response.setResult(save);
        return response;
    }
}
