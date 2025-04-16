package com.su.ac.th.project.grader.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SubmissionsResponse {

    private Long submissionId;
    private Long userId;
    private Long problemId;
    private String code;
    private String language;
    private String status;
    private Integer score;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

}
