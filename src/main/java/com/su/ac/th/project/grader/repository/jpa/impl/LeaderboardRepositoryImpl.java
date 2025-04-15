package com.su.ac.th.project.grader.repository.jpa.impl;

import com.su.ac.th.project.grader.model.response.LeaderboardResponse;
import com.su.ac.th.project.grader.repository.jpa.LeaderboardRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.List;

@Repository
public class LeaderboardRepositoryImpl implements LeaderboardRepository {
    private final JdbcTemplate jdbcTemplate;

    public LeaderboardRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LeaderboardResponse> getLeaderboard(Integer offset, Integer limit) {
        String sql = "SELECT u.id, u.username, u.score, " +
                "ROW_NUMBER() OVER(ORDER BY u.score DESC) AS `rank` " +
                "FROM users u " +
                "ORDER BY u.score DESC " +
                "LIMIT ?, ?";

        return jdbcTemplate.query(sql,
                new Object[]{offset * limit, limit},
                new int[]{java.sql.Types.BIGINT, java.sql.Types.BIGINT},
                (rs, rowNum) -> {
                    LeaderboardResponse entry = new LeaderboardResponse();
                    entry.setId(rs.getLong("id"));
                    entry.setUsername(rs.getString("username"));
                    entry.setScore(rs.getLong("score"));
                    entry.setRank(rs.getInt("rank"));
                    return entry;
                });
    }

    @Override
    public LeaderboardResponse getUserRanking(Long userId) {
        String sql = "SELECT u.id, u.username, u.score, u.rank " +
                "FROM (SELECT ranked.*, ROW_NUMBER() OVER (ORDER BY score DESC) AS 'rank' " +
                "      FROM users ranked) u " +
                "WHERE u.id = ?";

        List<LeaderboardResponse> results = jdbcTemplate.query(
                sql,
                new Object[]{userId},
                new int[]{Types.BIGINT},
                (rs, rowNum) -> {
                    LeaderboardResponse entry = new LeaderboardResponse();
                    entry.setId(rs.getLong("id"));
                    entry.setUsername(rs.getString("username"));
                    entry.setScore(rs.getLong("score"));
                    entry.setRank(rs.getInt("rank"));
                    return entry;
                });

        return results.isEmpty() ? null : results.getFirst();
    }

    public Integer getTotalCount() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM users", Integer.class);
    }

}
