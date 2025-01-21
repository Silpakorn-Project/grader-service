package com.su.ac.th.project.grader.model.request;

import lombok.Data;

@Data
public class ProblemUpdateRequest {

    private Long problemId;
    private String title;
    private String description;
    private String difficulty;
    private String type;

}
