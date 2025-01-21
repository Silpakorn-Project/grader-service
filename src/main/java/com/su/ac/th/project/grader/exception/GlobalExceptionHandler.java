package com.su.ac.th.project.grader.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<String> handleException(BaseException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
