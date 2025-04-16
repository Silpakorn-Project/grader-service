package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.constant.HttpConstant;
import com.su.ac.th.project.grader.model.BaseResponseModel;
import com.su.ac.th.project.grader.model.PaginationResponse;
import com.su.ac.th.project.grader.model.response.LeaderboardResponse;
import com.su.ac.th.project.grader.service.LeaderboardService;
import jakarta.annotation.security.PermitAll;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
    private final LeaderboardService leaderboardService;

    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @GetMapping
    public ResponseEntity<BaseResponseModel> getLeaderboard(
            @RequestParam(required = true) Integer offset,
            @RequestParam(required = true) Integer limit
    ) {
        PaginationResponse<LeaderboardResponse> response = leaderboardService.getLeaderboard(offset, limit);

        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .offset(offset)
                .limit(limit)
                .totalRecords(response.getTotalRecords())
                .totalPages(response.getTotalPages())
                .dataCount(response.getData().size())
                .data(response.getData())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModel> getUserRanking(@PathVariable("id") Long userId) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(leaderboardService.getUserRanking(userId))
                .build());
    }
}
