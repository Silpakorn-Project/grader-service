package com.su.ac.th.project.grader.exception.authentication;

public class InvalidUsernameOrPasswordException extends RuntimeException {
    public InvalidUsernameOrPasswordException() {
        super("Invalid username or password");
    }
}
