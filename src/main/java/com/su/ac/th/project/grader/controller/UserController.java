package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.model.request.UsersRequest;
import com.su.ac.th.project.grader.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create/user")
    public UsersRequest createUser(@RequestBody UsersRequest usersRequest){
        return userService.createUser(usersRequest);
    }

}
