package com.su.ac.th.project.grader.service.external.response;

import lombok.Data;

@Data
public class TestResult {
    private boolean passed;
    private String input;
    private String expected;
    private String actual;
    private String error;
}
