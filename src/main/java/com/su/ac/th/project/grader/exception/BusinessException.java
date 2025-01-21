package com.su.ac.th.project.grader.exception;

public class BusinessException extends BaseException{

    public BusinessException(String message) {
        super("business." + message);
    }

    public static BusinessException notFound(String message) {
        return new BusinessException("not.found." + message);
    }

}

