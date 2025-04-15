package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.model.PaginationResponse;
import com.su.ac.th.project.grader.model.response.LeaderboardResponse;
import com.su.ac.th.project.grader.repository.jpa.LeaderboardRepository;
import com.su.ac.th.project.grader.util.PaginationUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaderboardService {
    private final LeaderboardRepository leaderboardRepository;

    public LeaderboardService(LeaderboardRepository leaderboardRepository) {
        this.leaderboardRepository = leaderboardRepository;
    }

    public PaginationResponse<LeaderboardResponse> getLeaderboard(Integer offset, Integer limit) {
        if (PaginationUtil.isPaginationValid(offset, limit)) {
            List<LeaderboardResponse> entries = leaderboardRepository.getLeaderboard(offset - 1, limit);
            Integer totalCount = leaderboardRepository.getTotalCount();
            Integer totalPages = (totalCount + limit - 1) / limit;

            return PaginationResponse.<LeaderboardResponse>builder()
                    .data(entries)
                    .totalRecords(totalCount.longValue())
                    .totalPages(totalPages)
                    .build();
        }

        throw new RuntimeException();
    }

    public LeaderboardResponse getUserRanking(Long userId) {
        LeaderboardResponse result = leaderboardRepository.getUserRanking(userId);

        if (result == null) {
            throw new RuntimeException("User not found or has no score.");
        }

        return result;
    }
}
