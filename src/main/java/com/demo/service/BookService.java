package com.demo.service;

import com.demo.dao.BookroomDao;
import com.demo.domain.*;
import com.demo.dto.*;
import com.demo.dto.MindInsertDto;
import com.demo.dto.PictureInsertDto;
import com.demo.dto.RecommendBookFavoriteDto;
import com.demo.dto.WordInsertDto;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.demo.dto.DefaultFavoriteInsert.dtoToEntity;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookService {
    private final BookroomDao bookroomDao;
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
    public Roomview selectBookRoom(Long bookId, Long userId) {
        Roomview roomView = roomViewRepo.findByBookIdAndUserId(bookId, userId);
        return roomView;
    }

    public Bookroom saveBookRoom(BookRoomInsertDto bookRoomInsertDto) {
        insertDefaultCharacter(bookRoomInsertDto);
        //기본 캐릭터를 바탕으로 bookroom기본 캐릭터 생성
        updateBookPopularity(bookRoomInsertDto);
        //bookroom을 생성하면서 bookdetails에서 popularity를 +1함

        Bookroom bookroom = bookRoomInsertDto.dtoToBookRoom(bookRoomInsertDto);
        Bookroom save = bookRoomRepo.save(bookroom);
        log.info("bookroom save = {}", save);
        //생성

        insertDefaultFavorite(bookroom);
        //좋아요 표시 안한 상태로 favorite 생성
        return save;
    }

    //popularity 업데이트 시키는 메소드
    private void updateBookPopularity(BookRoomInsertDto bookRoomInsertDto) {
        Optional<Bookdetails> optionalBookdetails = bookDetailsRepo.findById(bookRoomInsertDto.getBookId());
        Bookdetails bookdetails = optionalBookdetails.get();
        bookdetails.updatePopularity();
        log.info("bookdetails update 실행");
    }

    //bookroom 생성시 isfavorite를 0으로하여 등록하는 메소드
    private void insertDefaultFavorite(Bookroom bookroom) {
        Favorite favorite = dtoToEntity(bookroom.getUserId(), bookroom.getBookId(), bookroom.getBookroomId());
        Favorite favoriteSave = favoriteRepo.save(favorite);
        log.info("default favorite save = {}", favoriteSave);
    }

    //themeColor 추가용(업데이트)
    public void updateThemeColor(String themeColor, Long bookroomId) {
        Bookroom bookroom = getBookroom(bookroomId);
        bookroom.updateThemeColor(themeColor);
        log.info("update themeColor = {}", themeColor);
    }

    //themeMusicUrl 추가용(업데이트)
    public void updateThemeMusicUrl(String themeMusicUrl, Long bookroomId) {
        Bookroom bookroom = getBookroom(bookroomId);
        bookroom.updateThemeMusicUrl(themeMusicUrl);
        log.info("update themeMusicUrl = {}", themeMusicUrl);
    }
    
    //bookroom정보 가져오기
    private Bookroom getBookroom(Long bookroomId) {
        Optional<Bookroom> optionalBookroom = bookRoomRepo.findById(bookroomId);
        Bookroom bookroom = optionalBookroom.get();
        return bookroom;
    }

    //기본 케릭터 가져와서 bookroom생성시 등록하는 메소드
    private void insertDefaultCharacter(BookRoomInsertDto bookRoomInsertDto) {
        Usercharacter defaultCharacter = userCharacterRepo.findByUserIdAndBookId(bookRoomInsertDto.getUserId(), 0L);
        Usercharacter usercharacter = DefaultCharacterDto.dtoToEntity(defaultCharacter, bookRoomInsertDto.getBookId());

        Usercharacter characterSave = userCharacterRepo.save(usercharacter);
        log.info("default character save = {}", characterSave);
    }

    public void deleteBookRoom(Long bookroomId) {
        Bookroom bookroom = getBookroom(bookroomId);
        bookRoomRepo.delete(bookroom);
        pictureRepo.deleteById(bookroomId);
        wordRepo.deleteByBookroomId(bookroomId);
        mindMapRepo.deleteByBookroomId(bookroomId);
    }

    public List<Picturetable> getPictureByBookroomId(Long bookroomId) {
        List<Picturetable> picturetables = pictureRepo.findAllByBookroomId(bookroomId);
        return picturetables;
    }
    public Picturetable savePicture(PictureInsertDto pictureInsertDto) {
        Picturetable picturetable = pictureInsertDto.insertDtoToPicturetable(pictureInsertDto);
        Picturetable save = pictureRepo.save(picturetable);
        log.info("picture save = {}", save);
        return save;
    }

    public List<Wordtable> getWordByroomId(Long bookroomId) {
        List<Wordtable> wordtables = wordRepo.findAllByBookroomId(bookroomId);
        return wordtables;
    }
    public Wordtable saveWord(WordInsertDto wordInsertDto) {
        Wordtable wordtable = wordInsertDto.insertDtoToWordtable(wordInsertDto);
        Wordtable save = wordRepo.save(wordtable);
        log.info("word save = {}", save);
        return save;
    }

    public List<Mindmap> getMindmapByBookroomId(Long bookroomId) {
        List<Mindmap> mindmaps = mindMapRepo.findAllByBookroomId(bookroomId);
        return mindmaps;
    }
    public Mindmap saveMind(MindInsertDto mindInsertDto) {
        Mindmap mindmap = mindInsertDto.insertDtoToMindmap(mindInsertDto);
        Mindmap save = mindMapRepo.save(mindmap);
        log.info("mind save = {}", save);
        return save;
    }

    public List<RecommendBookFavoriteDto> getRecommendBooks(Long id){
        //유저가 좋아요를 눌러논 동화책방의 주인이 등록한 다른 동화책방을 추천으로 주기 -> null일 경우 고려
        List<RecommendBookFavoriteDto> recommendBookFavoriteDtoList = bookroomDao.getBookroomByFavorite(id);
        System.out.println(recommendBookFavoriteDtoList);
        //유저가 이전에 읽어본 동화책을 등록한 동화책방을 추천
        recommendBookFavoriteDtoList.addAll(bookroomDao.getBookroomByExperience(id));
        //가장 좋아요가 많은 동화책

        //가장 popular한 동화책방

        return recommendBookFavoriteDtoList;
    }
}
