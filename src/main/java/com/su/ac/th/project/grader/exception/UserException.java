package com.su.ac.th.project.grader.exception;

public class UserException extends BaseException{

    public UserException(String message) {
        super("User." + message);
    }

    public static UserException argsRequired(String argName) {
        return new UserException("ArgsRequired." + argName);
    }

}

