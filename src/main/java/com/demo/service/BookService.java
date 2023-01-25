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
    public Response selectBookRoom(Long bookId, Long userId) {
        boolean isFavorite = true;

        Roomview roomview = roomViewRepo.findByBookIdAndUserId(bookId, userId);
        if (roomview == null) {
            return new Response(ROOMVIEWSELECTERRORMESSAGE, ROOMVIEWSELECTERRORCODE);
        }

        if (favoriteRepo.findByUserIdAndBookroomId(userId, roomview.getBookroomId()).isEmpty())
            isFavorite = false;

        BookRoomResponse result = new BookRoomResponse(roomview.getBookroomId(), roomview.getUserId(), roomview.getBookId(), roomview.getCharacterId(),
                roomview.getThemeColor(), roomview.getThemeMusicUrl(), roomview.getBookTitle(), isFavorite, roomview.getGender(), roomview.getNickname(),
                roomview.getHeadStyle(), roomview.getHeadColor(), roomview.getTopStyle(), roomview.getTopColor(), roomview.getPantsStyle(), roomview.getPantsColor(),
                roomview.getShoesStyle(), roomview.getShoesColor(), roomview.getFaceColor(), roomview.getFaceStyle());

        return new Response(result, SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response saveBookRoom(Long userId, String bookTitle, String bookAuthor) {
        Long bookId = getBookId(bookTitle, bookAuthor);

        List<Long> bookIds = bookRoomRepo.findBookroomId(userId);
        if (bookIds.contains(bookId)) {
            return new Response(BOOKROOMDUPLICATEDMESSAGE, BOOKROOMDUPLICATEDCODE);
        }
        //bookroom을 생성하면서 bookdetails에서 popularity를 +1함
        updateBookPopularity(bookId);

        //기본 캐릭터를 바탕으로 bookroom기본 캐릭터 생성
        //만약 기존 캐릭터 조회가 안된다면 예외반환
        if (insertDefaultCharacter(userId, bookId) == null) {
            return new Response(USERCHARACTERSELECTERRORMESSAGE, USERCHARACTERSELECTERRORCODE);
        }

        BookRoomInsertDto bookRoomInsertDto = new BookRoomInsertDto(userId, bookId);
        Bookroom bookroom = bookRoomInsertDto.dtoToBookRoom(bookRoomInsertDto);
        Bookroom save = bookRoomRepo.save(bookroom);
        log.info("bookroom save = {}", save);
        //생성

        //성공
        return new Response(bookroom, SUCCESSMESSAGE, SUCCESSCODE);
    }

    private Long getBookId(String bookTitle, String bookAuthor) {
        Optional<Bookdetails> optionalBookdetails = bookDetailsRepo.findByBookTitleAndBookAuthor(bookTitle, bookAuthor);
        if (optionalBookdetails.isEmpty()) {
            BookInsertDto bookInsertDto = new BookInsertDto(bookTitle, bookAuthor);
            Bookdetails save = bookDetailsRepo.save(bookInsertDto.dtoToBookdetails());
            return save.getBookId();
        } else {
            Bookdetails bookdetails = optionalBookdetails.get();
            //중복되는 북룸을 생성하는지 체크
            return bookdetails.getBookId();
        }
    }
    //book detail에 검색한 동화책 파싱하는 작업도 해야할 것 같습니다..! book details에서 값을 찾아서 popularity를 올리는 경우는
    //검색한 도서가 이미 book details테이블에 있는 경우이고
    //검색 도서가 book details에 없다면 테이블에 값을 넣고 popularity를 0으로 세팅 해야할 것 같아요! -> 안드로이드 파트와 이야기 후 결정
    //popularity 업데이트 시키는 메소드
    private Bookdetails updateBookPopularity(Long bookId) {
        Bookdetails bookdetails = bookDetailsRepo.findById(bookId).get();
        bookdetails.updatePopularity();
        log.info("bookdetails update 실행");
        return bookdetails;
    }

    //기본 케릭터 가져와서 bookroom생성시 등록하는 메소드
    private Usercharacter insertDefaultCharacter(Long userId, Long bookId) {
        Optional<Usercharacter> optionalUsercharacter = userCharacterRepo.findByUserIdAndBookId(userId, 0L);
        if (optionalUsercharacter.isEmpty()) {
            return null;
        }
        Usercharacter defaultCharacter = optionalUsercharacter.get();
        Usercharacter usercharacter = DefaultCharacterDto.dtoToEntity(defaultCharacter, bookId);

        Usercharacter characterSave = userCharacterRepo.save(usercharacter);
        log.info("default character save = {}", characterSave);
        return characterSave;
    }

    //bookroom 생성시 isfavorite를 0으로하여 등록하는 메소드
    //!!!삭제함!!!

    //themeColor 추가용(업데이트)
    public Response updateThemeColor(String themeColor, Long bookroomId) {
        Optional<Bookroom> optionalBookroom = getBookroom(bookroomId);
        if (optionalBookroom.isEmpty())
            return new Response(BOOKROOMSELECTERRORMESSAGE, BOOKROOMSELECTERRORCODE);
        Bookroom bookroom = optionalBookroom.get();
        bookroom.updateThemeColor(themeColor);
        log.info("update themeColor = {}", themeColor);

        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }

    //themeMusicUrl 추가용(업데이트)
    public Response updateThemeMusicUrl(String themeMusicUrl, Long bookroomId) {
        Optional<Bookroom> optionalBookroom = getBookroom(bookroomId);
        if (optionalBookroom.isEmpty())
            return new Response(BOOKROOMSELECTERRORMESSAGE, BOOKROOMSELECTERRORCODE);
        Bookroom bookroom = optionalBookroom.get();
        bookroom.updateThemeMusicUrl(themeMusicUrl);
        log.info("update themeMusicUrl = {}", themeMusicUrl);

        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }

    //bookroom정보 가져오기
    private Optional<Bookroom> getBookroom(Long bookroomId) {
        Optional<Bookroom> optionalBookroom = bookRoomRepo.findById(bookroomId);
        return optionalBookroom;
    }

    public Response deleteBookRoom(Long bookroomId) {
        Optional<Bookroom> optionalBookroom = getBookroom(bookroomId);
        if (optionalBookroom.isEmpty())
            return new Response(BOOKROOMSELECTERRORMESSAGE, BOOKROOMSELECTERRORCODE);
        Bookroom bookroom = optionalBookroom.get();
        bookRoomRepo.delete(bookroom);
        pictureRepo.deleteAllByBookroomId(bookroomId);
        wordRepo.deleteAllByBookroomId(bookroomId);
        mindMapRepo.deleteAllByBookroomId(bookroomId);
        userCharacterRepo.deleteByUserIdAndBookId(bookroom.getUserId(), bookroom.getBookId());
        favoriteRepo.deleteAllByBookroomId(bookroomId);
        return new Response(SUCCESSMESSAGE, SUCCESSCODE);

    }

    public Response getPictureByBookroomId(Long bookroomId) {
        List<Picturetable> picturetables = pictureRepo.findAllByBookroomId(bookroomId);
        return new Response(picturetables, SUCCESSMESSAGE, SUCCESSCODE);
    }
    public Response savePicture(PictureInsertDto pictureInsertDto) {
        Picturetable picturetable = pictureInsertDto.insertDtoToPicturetable(pictureInsertDto);
        Picturetable save = pictureRepo.save(picturetable);
        log.info("picture save = {}", save);

        return new Response(save, SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response getWordByroomId(Long bookroomId) {
        List<Wordtable> wordtables = wordRepo.findAllByBookroomId(bookroomId);
        return new Response(wordtables, SUCCESSMESSAGE, SUCCESSCODE);
    }
    public Response saveWord(WordInsertDto wordInsertDto) {
        Wordtable wordtable = wordInsertDto.insertDtoToWordtable(wordInsertDto);
        Wordtable save = wordRepo.save(wordtable);
        log.info("word save = {}", save);

        return new Response(save, SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response getMindmapByBookroomId(Long bookroomId) {
        List<Mindmap> mindmaps = mindMapRepo.findAllByBookroomId(bookroomId);
        return new Response(mindmaps, SUCCESSMESSAGE, SUCCESSCODE);
    }
    public Response saveMind(MindInsertDto mindInsertDto) {
        Mindmap mindmap = mindInsertDto.insertDtoToMindmap(mindInsertDto);
        Mindmap save = mindMapRepo.save(mindmap);
        log.info("mind save = {}", save);

        return new Response(save, SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response updatePicture(Long pictureId, String pictureUrl) {
        Optional<Picturetable> optionalPicturetable = pictureRepo.findById(pictureId);
        if (optionalPicturetable.isEmpty())
            return new Response(PICTURESELECTERRORMESSAGE, PICTURESELECTERRORCODE);
        Picturetable picturetable = optionalPicturetable.get();
        picturetable.updatePictureUrl(pictureUrl);
        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response updateWord(Long wordId, String wordText, String wordPictureUrl, String wordVoiceUrl) {
        Optional<Wordtable> optionalWordtable = wordRepo.findById(wordId);
        if (optionalWordtable.isEmpty())
            return new Response(WORDSELECTERRORMESSAGE, WORDSELECTERRORCODE);
        Wordtable wordtable = optionalWordtable.get();
        wordtable.updateWord(wordPictureUrl, wordVoiceUrl, wordText);
        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response updateMind(Long mindId, String wordText, String wordPictureUrl, String wordVoiceUrl) {
        Optional<Mindmap> optionalMindmap = mindMapRepo.findById(mindId);
        if (optionalMindmap.isEmpty())
            return new Response(MINDMAPSELECTERRORMESSAGE, MINDMAPSELECTERRORCODE);
        Mindmap mindmap = optionalMindmap.get();
        mindmap.updateMind(wordText, wordPictureUrl, wordVoiceUrl);

        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response deletePicture(Long pictureId) {
        pictureRepo.deleteById(pictureId);
        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response deleteWord(Long wordId) {
        wordRepo.deleteById(wordId);
        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }

    public Response deleteMind(Long mindId) {
        mindMapRepo.deleteById(mindId);
        return new Response(SUCCESSMESSAGE, SUCCESSCODE);
    }
}
