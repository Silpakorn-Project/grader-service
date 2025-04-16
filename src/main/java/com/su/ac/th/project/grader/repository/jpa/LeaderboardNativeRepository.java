package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.model.response.LeaderboardResponse;

import java.util.List;

public interface LeaderboardNativeRepository {
    List<LeaderboardResponse> getLeaderboard(Integer offset, Integer limit);

    LeaderboardResponse getUserRanking(Long userId);

    Integer getTotalCount();

}
