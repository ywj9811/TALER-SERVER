package com.demo.dao;

import com.demo.dto.RecommendFriendDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    //과거 읽어본 동화책을 등록한 다른 유저
    public List<RecommendFriendDto> recommendFriendByFavoriteExperience(Long userId) {
        List<RecommendFriendDto> results = jdbcTemplate.query(
                "select u.user_id,u.profile_color,u.nickname " +
                        "from bookroom b join user u on b.user_id = u.user_id " +
                        "where book_id in( " +
                        "select book_id from favorite where user_id = ? and isfavorite=0);",
                new RowMapper<RecommendFriendDto>() {
                    @Override
                    public RecommendFriendDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                        RecommendFriendDto recommendFriendDto = new RecommendFriendDto(
                                rs.getLong("user_id"),
                                rs.getString("profile_color"),
                                rs.getString("nickname")
                        );
                        return recommendFriendDto;
                    }
                }, userId);

        return results.isEmpty() ? null : results;
    }

    //나이가 같은 다른 user
    public List<RecommendFriendDto> recommendFriendBySameAge(Long userId) {
        List<RecommendFriendDto> results = jdbcTemplate.query(
                "select user_id,profile_color,nickname " +
                        "from user " +
                        "where age = (" +
                        "select age " +
                        "from user " +
                        "where user_id=?);",
                new RowMapper<RecommendFriendDto>() {
                    @Override
                    public RecommendFriendDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                        RecommendFriendDto recommendFriendDto = new RecommendFriendDto(
                                rs.getLong("user_id"),
                                rs.getString("profile_color"),
                                rs.getString("nickname")
                        );
                        return recommendFriendDto;
                    }
                }, userId);

        return results.isEmpty() ? null : results;
    }
    //등록한 동화책이 같은 경우가 2개 이상인 경우
    public List<RecommendFriendDto> recommendFriendBySameBook(Long userId) {
        List<RecommendFriendDto> results = jdbcTemplate.query(
//                "select u.user_id,u.profile_color,u.nickname " +
//                        "from bookroom b join user u on b.user_id = u.user_id " +
//                        "where b.book_id = (select * from (" +
//                        "select book_id " +
//                        "from bookroom " +
//                        "where user_id = ? limit 1) as tmp);",
                "select u.user_id,u.profile_color,u.nickname " +
                "from bookroom b join user u on b.user_id = u.user_id " +
                        "where b.book_id >= 2 and b.book_id in (" +
                        "select book_id " +
                        "from bookroom " +
                        "where user_id = ?);",
                new RowMapper<RecommendFriendDto>() {
                    @Override
                    public RecommendFriendDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                        RecommendFriendDto recommendFriendDto = new RecommendFriendDto(
                                rs.getLong("user_id"),
                                rs.getString("profile_color"),
                                rs.getString("nickname")
                        );
                        return recommendFriendDto;
                    }
                }, userId);

        return results.isEmpty() ? null : results;
    }

    //좋아요를 누른 동화책이 3개 이상인 경우
    public List<RecommendFriendDto> recommendFriendBySameFavoriteBook(Long userId) {
        List<RecommendFriendDto> results = jdbcTemplate.query(
                "select u.user_id,u.profile_color,u.nickname " +
                        "from user u join favorite f on u.user_id = f.user_id " +
                        "where f.book_id >= 3 and f.book_id in (" +
                        "select book_id " +
                        "from favorite " +
                        "where user_id = ?);",
                new RowMapper<RecommendFriendDto>() {
                    @Override
                    public RecommendFriendDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                        RecommendFriendDto recommendFriendDto = new RecommendFriendDto(
                                rs.getLong("user_id"),
                                rs.getString("profile_color"),
                                rs.getString("nickname")
                        );
                        return recommendFriendDto;
                    }
                }, userId);

        return results.isEmpty() ? null : results;
    }
}
