package com.su.ac.th.project.grader.exception;

import com.su.ac.th.project.grader.exception.authentication.AuthenticationException;
import com.su.ac.th.project.grader.exception.authentication.DuplicateUserException;
import com.su.ac.th.project.grader.exception.authentication.InvalidRefreshTokenException;
import com.su.ac.th.project.grader.exception.authentication.InvalidUsernameOrPasswordException;
import com.su.ac.th.project.grader.exception.problem.ProblemNotFoundException;
import com.su.ac.th.project.grader.exception.sandbox.SandboxClientException;
import com.su.ac.th.project.grader.exception.submission.SubmissionNotFoundException;
import com.su.ac.th.project.grader.exception.testcase.TestCaseNotFoundException;
import com.su.ac.th.project.grader.exception.testcase.TestCasesNotFoundForProblemIdException;
import com.su.ac.th.project.grader.exception.user.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.su.ac.th.project.grader.constant.HttpConstant.Status;
import static com.su.ac.th.project.grader.constant.HttpConstant.Message;
import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            Exception.class,
            SandboxClientException.class
    })
    public ResponseEntity<BaseException> handleInternalServerError(Exception exception) {
        log.error(exception.getMessage(), exception);
        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(Message.INTERNAL_SERVER_ERROR)
                .code(Status.INTERNAL_SERVER_ERROR)
                .build();

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            DuplicateUserException.class
    })
    public ResponseEntity<BaseException> handleConflictError(Exception ex) {
        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(ex.getMessage())
                .code(Status.CONFLICT)
                .build();

        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            NoResourceFoundException.class,
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

    @ExceptionHandler({
            AuthenticationException.class,
            InvalidUsernameOrPasswordException.class,
            InvalidRefreshTokenException.class
    })
    public ResponseEntity<BaseException> handleAuthenticationException(Exception ex) {
        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message(ex.getMessage())
                .code(Status.UNAUTHORIZED)
                .build();

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseException> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> validationErrors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String fieldName = error.getField();
            String rejectedValue = String.valueOf(error.getRejectedValue());
            validationErrors.put(fieldName, String.format("Invalid value '%s'", rejectedValue));
        });

        BaseException body = BaseException.builder()
                .timestamp(getDateTimeNow())
                .message("Validation failed, see data for more details.")
                .code(Status.BAD_REQUEST)
                .data(validationErrors)
                .build();

        return ResponseEntity.badRequest().body(body);
    }
}