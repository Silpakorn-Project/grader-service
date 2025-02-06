package com.su.ac.th.project.grader.model.response;

import lombok.Data;

@Data
public class RunTestResponse {
    private boolean passed;
    private int testcase_total;
    private int testcase_passed;
    private int testcase_wrong;
    private TestResult[] test_cases;

    @Data
    public static class TestResult {
        private boolean passed;
        private String input;
        private String expected;
        private String actual;
        private String error;
    }
}
