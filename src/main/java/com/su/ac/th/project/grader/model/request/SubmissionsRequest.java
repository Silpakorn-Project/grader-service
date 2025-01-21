package com.su.ac.th.project.grader.model.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SubmissionsRequest {

    private Long userId;
    private Long problemId;
    private String code;
    private String language;
    private String status;
    private BigDecimal scorePercent;

}
