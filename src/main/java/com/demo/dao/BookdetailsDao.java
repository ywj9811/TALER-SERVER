package com.demo.dao;

import com.demo.dto.RecommendBookFavoriteDto;
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
                "select distinct book_genre " +
                        "from bookdetails " +
                        "where book_id in(" +
                        "select br.book_id " +
                        "from bookroom br join favorite f on br.bookroom_id = f.bookroom_id " +
                        "where f.isfavorite = 0 and f.user_id = ?);",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                }, userId);


        return results.isEmpty() ? null : results;
    }
}
