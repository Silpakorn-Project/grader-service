package com.su.ac.th.project.grader.exception;

import com.su.ac.th.project.grader.exception.authentication.AuthenticationException;
import com.su.ac.th.project.grader.exception.problem.ProblemNotFoundException;
import com.su.ac.th.project.grader.exception.sandbox.SandboxClientException;
import com.su.ac.th.project.grader.exception.sandbox.UnsupportedLanguageException;
import com.su.ac.th.project.grader.exception.submission.SubmissionNotFoundException;
import com.su.ac.th.project.grader.exception.testcase.TestCaseNotFoundException;
import com.su.ac.th.project.grader.exception.testcase.TestCasesNotFoundForProblemIdException;
import com.su.ac.th.project.grader.exception.user.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

import static com.su.ac.th.project.grader.constant.HttpConstant.Status;
import static com.su.ac.th.project.grader.constant.HttpConstant.Message;
import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            Exception.class,
            SandboxClientException.class
    })
    public ResponseEntity<BaseException> handleInternalServerError(Exception exception) {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.error(exception.getMessage(), exception);

        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(Message.INTERNAL_SERVER_ERROR)
                .code(Status.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UnsupportedLanguageException.class)
    public ResponseEntity<BaseException> handleBadRequestError(Exception exception) {
        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(exception.getMessage())
                .code(Status.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<BaseException> handleAuthenticationError(Exception exception) {
        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(exception.getMessage())
                .code(Status.UNAUTHORIZED)
                .build();

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({
            ProblemNotFoundException.class,
            SubmissionNotFoundException.class,
            TestCaseNotFoundException.class,
            TestCasesNotFoundForProblemIdException.class,
            UserNotFoundException.class
    })
    public ResponseEntity<BaseException> handleNotFoundError(Exception exception) {
        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(exception.getMessage())
                .code(Status.NOT_FOUND)
                .build();

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseException> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(errors.toString())
                .code(Status.BAD_REQUEST)
                .build();

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}