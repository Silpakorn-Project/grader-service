package com.su.ac.th.project.grader.exception;

import com.su.ac.th.project.grader.exception.problem.ProblemNotFoundException;
import com.su.ac.th.project.grader.exception.submission.SubmissionNotFoundException;
import com.su.ac.th.project.grader.exception.testcase.TestCaseNotFoundException;
import com.su.ac.th.project.grader.exception.testcase.TestCasesNotFoundForProblemIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.su.ac.th.project.grader.constant.HttpConstant.Message;
import static com.su.ac.th.project.grader.constant.HttpConstant.Status;
import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseException> handleInternalError(Exception exception) {
        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(Message.INTERNAL_SERVER_ERROR)
                .code(Status.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            ProblemNotFoundException.class,
            SubmissionNotFoundException.class,
            TestCaseNotFoundException.class,
            TestCasesNotFoundForProblemIdException.class
    })
    public ResponseEntity<BaseException> handleNotFoundError(Exception exception) {
        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(exception.getMessage())
                .code(Status.NOT_FOUND)
                .build();

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
}