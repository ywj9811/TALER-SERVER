package com.demo.service;

import com.demo.domain.*;
import com.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRoomRepo bookRoomRepo;
    private final BookDetailsRepo bookDetailsRepo;
    private final PictureRepo pictureRepo;
    private final WordRepo wordRepo;
    private final MindMapRepo mindMapRepo;

    /**
     * 기본 외에 제공해야 할 데이터
     * 1. bookId를 통한 책 제목
     * 2. 그리고 그냥 bookroom을 통째로 제공하여도 괜찮을지
     */
//    public BookRoomPlusBookDetails moveToBookRoom(Long userId, Long bookId) {
//        BookRoomPlusBookDetails bookRoomPlusBookDetails = bookRoomRepo.findBookTitle(userId);
//        return bookRoomPlusBookDetails;
//    }

    public Map<String, Object> getBookRoom(Long bookId, Long userId) {
        Map<String, Object> returnMap = new HashMap<>();

        Optional<Bookroom> optionalBookroom = bookRoomRepo.findByUserIdAndBookId(bookId, userId);
        Optional<Bookdetails> optionalBookdetails = bookDetailsRepo.findById(bookId);
        if (optionalBookdetails.isEmpty() || optionalBookroom.isEmpty())
            return null;
        Bookroom bookroom = optionalBookroom.get();
        Bookdetails bookdetails = optionalBookdetails.get();
        returnMap.put("bookroom", bookroom);
        returnMap.put("bookdetails", bookdetails);

        return returnMap;
    }

    public List<Picturetable> getPictureByBookroomId(Long bookroomId) {
        List<Picturetable> picturetables = pictureRepo.findAllByBookroomId(bookroomId);
        return picturetables;
    }
    public List<Wordtable> getWordByroomId(Long bookroomId) {
        List<Wordtable> wordtables = wordRepo.findAllByBookroomId(bookroomId);
        return wordtables;
    }
    public List<Mindmap> getMindmapByBookroomId(Long bookroomId) {
        List<Mindmap> mindmaps = mindMapRepo.findAllByBookroomId(bookroomId);
        return mindmaps;
    }
}
