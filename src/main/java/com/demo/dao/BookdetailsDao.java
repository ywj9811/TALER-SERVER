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

    public List<String> getBookTitlesByPopularity() {
        List<String> results = jdbcTemplate.query(
                "select book_title " +
                        "from bookdetails " +
                        "where book_popularity >= ( " +
                        "select ((max(book_popularity) + min(book_popularity))/2) " +
                        "from bookdetails);",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                });

        return results.isEmpty() ? null : results;
    }

    public List<String> getBookTitlesByFavorite(long userId) {
        List<String> results = jdbcTemplate.query(
                "select book_title " +
                        "from bookdetails " +
                        "where book_id in (" +
                        "select book_id from favorite " +
                        "where user_id = ? and book_id != (select book_id from bookroom where bookroom.user_id = ?));",
                new RowMapper<String>() {
                    @Override
                    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return rs.getString(1);
                    }
                },userId);

        return results.isEmpty() ? null : results;
    }
}
