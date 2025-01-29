package com.su.ac.th.project.grader.exception.problem;

public class ProblemNotFoundException extends RuntimeException {

    public ProblemNotFoundException(Long problemId) {
        super(String.format("Problem id %d is not found", problemId));
    }
}
