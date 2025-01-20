package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.model.request.UsersRequest;
import com.su.ac.th.project.grader.service.UsersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @PostMapping("/register")
    public String register(@RequestBody UsersRequest usersRequest){



        return UsersService.register();
    }

    @PostMapping("/login")
    public String login(){
        return "login";
    }



}
