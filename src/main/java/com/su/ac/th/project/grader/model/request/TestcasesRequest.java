package com.su.ac.th.project.grader.model.request;

import lombok.Data;

@Data
public class TestcasesRequest {

    private Long problemId;
    private String inputData;
    private String expectedOutput;

}
