package com.su.ac.th.project.grader.model.response;

import lombok.Data;

@Data
public class LeaderboardResponse {
    private Long id;
    private String username;
    private Long score;
    private Integer rank;
}