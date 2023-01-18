package com.demo.dao;

import com.demo.dto.BookRoomSelectDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookroomDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //좋아요 눌러논 동화책을 등록한 다른 유저의 동화책방 정보
    public List<BookRoomSelectDto> getBookroomByFavorite(Long userId) {
        List<BookRoomSelectDto> results = jdbcTemplate.query(
                "select distinct user_id,book_id,bookroom_id,theme_color from bookroom " +
                        "where bookroom_id in(select bookroom_id " +
                        "from favorite " +
                        "where user_id = ? and isfavorite = 1);",
                new RowMapper<BookRoomSelectDto>() {
                    @Override
                    public BookRoomSelectDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                        BookRoomSelectDto bookRoomSelectDto = new BookRoomSelectDto(
                                rs.getLong("user_id"),
                                rs.getLong("book_id"),
                                rs.getLong("bookroom_id"),
                                rs.getString("theme_color"));
                        return bookRoomSelectDto;
                    }
                }, userId);

        return results.isEmpty() ? null : results;
    }


    //유저가 과거 읽어본 동화책을 등록한 동화책 방들의 정보
    public List<BookRoomSelectDto> getBookroomByExperience(Long userId) {
        List<BookRoomSelectDto> results = jdbcTemplate.query(
                "select distinct bookroom_id,book_id,theme_color,user_id from bookroom\n" +
                        "where user_id != ? and book_id in(" +
                        "select book_id " +
                        "from favorite " +
                        "where user_id = ? and isfavorite = 0 " +
                        ");",
                new RowMapper<BookRoomSelectDto>() {
                    @Override
                    public BookRoomSelectDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                        BookRoomSelectDto bookRoomSelectDto = new BookRoomSelectDto(
                                rs.getLong("user_id"),
                                rs.getLong("book_id"),
                                rs.getLong("bookroom_id"),
                                rs.getString("theme_color"));
                        return bookRoomSelectDto;
                    }
                }, userId, userId);

        return results.isEmpty() ? null : results;
    }

}
