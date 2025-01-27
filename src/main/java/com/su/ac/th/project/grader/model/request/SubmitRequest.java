package com.su.ac.th.project.grader.model.request;

import lombok.Data;

@Data
public class SubmitRequest {

    private Long userId;
    private Long problemId;
    private String code;
    private String language;
}
