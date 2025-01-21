package com.su.ac.th.project.grader.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubmissionsUpdateRequest {

    private Long submissionId;
    private Long userId;
    private Long problemId;
    private String code;
    private String language;
    private String status;
    private BigDecimal scorePercent;

}
