package com.su.ac.th.project.grader.service.external.response;

import lombok.Data;

@Data
public class RunTestResponse {
    private boolean passed;
    private int testcase_total;
    private int testcase_passed;
    private int testcase_wrong;
    private TestResult[] test_cases;
}
