package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.constant.HttpConstant.*;
import com.su.ac.th.project.grader.model.BaseResponseModel;
import com.su.ac.th.project.grader.model.request.authentication.UsersLoginRequest;
import com.su.ac.th.project.grader.model.request.authentication.UsersRegRequest;
import com.su.ac.th.project.grader.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponseModel> register(@Valid @RequestBody UsersRegRequest usersRegRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .code(Status.SUCCESS)
                .data(authenticationService.register(usersRegRequest))
                .message(Message.SUCCESS)
                .timestamp(getDateTimeNow())
                .build());
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponseModel> login(@Valid @RequestBody UsersLoginRequest usersLoginRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .code(Status.SUCCESS)
                .data(authenticationService.login(usersLoginRequest))
                .message(Message.SUCCESS)
                .timestamp(getDateTimeNow())
                .build());
    }



}
