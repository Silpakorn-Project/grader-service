package com.su.ac.th.project.grader.exception.authentication;

public class DuplicateUserException extends RuntimeException {
    public DuplicateUserException(String message) {
        super(message);
    }
}
