package com.su.ac.th.project.grader.exception.user;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long userId) {
        super(String.format("User with id %d is not found", userId));
    }

    public UserNotFoundException(String username) {
        super(String.format("User with username %s is not found", username));
    }
}
