package com.demo.service;

import com.demo.domain.Bookroom;
import com.demo.repository.BookRoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
    private final BookRoomRepo bookRoomRepo;

    public Bookroom findByUserIdAndBookId(Long userId, Long bookId) {
        return bookRoomRepo.findByUserIdAndBookId();
    }
}
