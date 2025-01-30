package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.model.request.UsersLoginRequest;
import com.su.ac.th.project.grader.model.request.UsersRegRequest;
import com.su.ac.th.project.grader.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public String register(@Valid @RequestBody UsersRegRequest usersRegRequest) {
        return authenticationService.register(usersRegRequest);
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody UsersLoginRequest usersLoginRequest) {
        return authenticationService.login(usersLoginRequest);
    }


}
