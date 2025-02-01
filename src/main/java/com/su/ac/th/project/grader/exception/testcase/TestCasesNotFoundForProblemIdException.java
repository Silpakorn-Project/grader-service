package com.su.ac.th.project.grader.exception.testcase;

public class TestCasesNotFoundForProblemIdException extends RuntimeException {

    public TestCasesNotFoundForProblemIdException(long problemId) {
        super(String.format("Test cases for problem id %d is not found", problemId));
    }
}
