package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.constant.HttpConstant.*;
import com.su.ac.th.project.grader.model.BaseResponseModel;
import com.su.ac.th.project.grader.model.request.authentication.UsersLoginRequest;
import com.su.ac.th.project.grader.model.request.authentication.UsersRegRequest;
import com.su.ac.th.project.grader.service.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponseModel> register(
            @Valid @RequestBody UsersRegRequest usersRegRequest
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .code(Status.SUCCESS)
                .data(authenticationService.register(usersRegRequest))
                .message(Message.SUCCESS)
                .timestamp(getDateTimeNow())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseModel> login(
            @Valid @RequestBody UsersLoginRequest usersLoginRequest, HttpServletResponse response
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .code(Status.SUCCESS)
                .data(authenticationService.login(usersLoginRequest, response))
                .message(Message.SUCCESS)
                .timestamp(getDateTimeNow())
                .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<BaseResponseModel> refresh(
            @CookieValue(name = "refresh_token", required = false) String refreshToken
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .code(Status.SUCCESS)
                .data(authenticationService.refreshAccessToken(refreshToken))
                .message(Message.SUCCESS)
                .timestamp(getDateTimeNow())
                .build());
    }

    @PostMapping("/logout")
    public ResponseEntity<BaseResponseModel> logout(HttpServletResponse response) {
        authenticationService.logout(response);
        return ResponseEntity.ok(BaseResponseModel.builder()
                .code(Status.SUCCESS)
                .message(Message.SUCCESS)
                .timestamp(getDateTimeNow())
                .build());
    }
}
