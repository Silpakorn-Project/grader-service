package com.su.ac.th.project.grader.exception.submission;

public class SubmissionNotFoundException extends RuntimeException {

    public SubmissionNotFoundException(long submissionId) {
        super(String.format("Submission id %d is not found", submissionId));
    }
}
