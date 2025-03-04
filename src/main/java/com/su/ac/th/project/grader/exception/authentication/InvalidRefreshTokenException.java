package com.su.ac.th.project.grader.exception.authentication;

public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException() {
        super("Invalid Refresh Token");
    }
}
