package com.su.ac.th.project.grader.exception.authentication;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String message) {
        super(message);
    }

    public static void invalidUsernameOrPassword() {
        throw new AuthenticationException("Invalid username or password");
    }


}
