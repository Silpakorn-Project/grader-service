package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.exception.user.UserNotFoundException;
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
            Integer totalRecords = leaderboardRepository.getTotalCount();
            int totalPages = (totalRecords + limit - 1) / limit;

            return PaginationUtil.createPaginationResponse(entries, totalRecords, totalPages);
        }

        List<LeaderboardResponse> allEntries = leaderboardRepository.getLeaderboard(0, Integer.MAX_VALUE);
        return  PaginationUtil.createPaginationResponse(allEntries, allEntries.size(), 1);
    }

    public LeaderboardResponse getUserRanking(Long userId) {
        LeaderboardResponse result = leaderboardRepository.getUserRanking(userId);

        if (result == null) {
            throw new UserNotFoundException(userId);
        }

        return result;
    }
}
