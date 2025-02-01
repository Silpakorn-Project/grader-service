package com.su.ac.th.project.grader.exception.testcase;

public class TestCaseNotFoundException extends RuntimeException {

    public TestCaseNotFoundException(long testCaseId) {
        super(String.format("Test case id %d is not found", testCaseId));
    }
}
