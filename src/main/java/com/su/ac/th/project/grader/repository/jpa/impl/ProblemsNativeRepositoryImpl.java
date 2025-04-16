package com.su.ac.th.project.grader.repository.jpa.impl;

import com.su.ac.th.project.grader.model.Projection.ProblemWithStatsProjection;
import com.su.ac.th.project.grader.model.request.problem.ProblemSearchCriteria;
import com.su.ac.th.project.grader.repository.jpa.ProblemsNativeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.ParsingUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProblemsNativeRepositoryImpl implements ProblemsNativeRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProblemsNativeRepositoryImpl(DataSource dataSource) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Page<ProblemWithStatsProjection> getProblemsWithStatus(Long userId, ProblemSearchCriteria criteria, Pageable pageable) {
        StringBuilder sql = new StringBuilder("""
                    SELECT p.problem_id AS problemId,
                           p.title AS title,
                           p.description AS description,
                           p.difficulty AS difficulty,
                           p.type AS type,
                           CASE
                             WHEN MAX(s.score) = 100 THEN 'Passed'
                             WHEN COUNT(s.submission_id) > 0 THEN 'Attempted'
                             ELSE 'Unattempted'
                           END AS status
                    FROM problems p
                    LEFT JOIN submissions s ON p.problem_id = s.problem_id AND s.user_id = :userId
                    WHERE 1=1
                """);

        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);

        if (criteria.getTitle() != null) {
            sql.append(" AND p.title LIKE :title ");
            params.put("title", "%" + criteria.getTitle() + "%");
        }
        if (criteria.getDescription() != null) {
            sql.append(" AND p.description LIKE :description ");
            params.put("description", "%" + criteria.getDescription() + "%");
        }
        if (criteria.getDifficulty() != null) {
            sql.append(" AND p.difficulty = :difficulty ");
            params.put("difficulty", criteria.getDifficulty().name());
        }
        if (criteria.getType() != null) {
            sql.append(" AND p.type = :type ");
            params.put("type", criteria.getType().name());
        }

        sql.append(" GROUP BY p.problem_id ");

        if (criteria.getStatus() != null) {
            sql.append(" HAVING ");
            sql.append("""
                        CASE
                          WHEN MAX(s.score) = 100 THEN 'Passed'
                          WHEN COUNT(s.submission_id) > 0 THEN 'Attempted'
                          ELSE 'Unattempted'
                        END = :status
                    """);
            params.put("status", criteria.getStatus());
        }

        String countSql = "SELECT COUNT(*) FROM (" + sql + ") AS count_query";

        if (pageable.getSort().isSorted()) {
            Sort.Order order = pageable.getSort().iterator().next();
            sql.append(" ORDER BY p.").append(ParsingUtils.reconcatenateCamelCase(order.getProperty(), "_"))
                    .append(" ").append(order.getDirection());
        }

        sql.append(" LIMIT :limit OFFSET :offset ");
        params.put("limit", pageable.getPageSize());
        params.put("offset", pageable.getOffset());

        List<ProblemWithStatsProjection> content = namedParameterJdbcTemplate.query(sql.toString(), params, new BeanPropertyRowMapper<>(ProblemWithStatsProjection.class));
        Integer totalCount = namedParameterJdbcTemplate.queryForObject(countSql, params, Integer.class);
        int total = totalCount != null ? totalCount : 0;

        return new PageImpl<>(content, pageable, total);
    }
}
