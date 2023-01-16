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
public class FavoriteDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Boolean checkFavorite(Long userId, Long bookId) {
        Integer cnt = jdbcTemplate.queryForObject(
                "select count(*) " +
                        "from favorite " +
                        "where user_id = ? and bookroom_id = ?;", Integer.class, userId,bookId);
        return cnt != null && cnt > 0;
    }
}
