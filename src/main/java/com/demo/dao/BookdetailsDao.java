package com.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BookdetailsDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    //읽어본 동화책으로 선택한 동화책의 장르 알아오기
    public List<String> myFavoriteGenreByExperience(Long userId) {
        List<String> results = jdbcTemplate.query(
                "select book_genre " +
                        "from bookdetails " +
                        "where book_id in (select book_id " +
                        "from favorite " +
                        "where user_id = ? " +
                        ");",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                }, userId);


        return results.isEmpty() ? null : results;
    }

    //읽어본 동화책으로 띄워줄 동화책은 이미 파싱이 다 되어있는 동화책
    public void setBookPopularity(Long bookId) {
        jdbcTemplate.update("update bookdetails " +
                "set book_popularity = book_popularity + 1 " +
                "where book_id = ?;",bookId);
    }

    //책 장르로 책 제목 뽑아오기
    public List<String> getBookTitleByBookGere(String bookGenre,Long userId) {
        List<String> results = jdbcTemplate.query(
                "select book_title " +
                        "from bookdetails " +
                        "where book_genre = ? and book_title not in (select book_title " +
                        "from bookdetails bd join bookroom b on bd.book_id = b.book_id " +
                        "where user_id=?);",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                }, bookGenre,userId);

        return results.isEmpty() ? null : results;
    }

    //검색 동화책이 이미 디테일 테이블에 있는지 조사후
}
