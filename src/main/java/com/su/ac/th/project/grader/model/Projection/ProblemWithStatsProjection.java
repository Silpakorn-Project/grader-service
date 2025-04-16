package com.su.ac.th.project.grader.model.Projection;

import lombok.Data;

@Data
public class ProblemWithStatsProjection {
    private Long problemId;
    private String title;
    private String description;
    private String difficulty;
    private String type;
    private Integer score;
    private String status;
}
