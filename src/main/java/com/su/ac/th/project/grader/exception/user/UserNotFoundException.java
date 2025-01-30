package com.su.ac.th.project.grader.exception.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long userId) {
        super(String.format("User id %d is not found", userId));
    }
}
