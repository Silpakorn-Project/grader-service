package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.dto.request.UsersRequest;
import com.su.ac.th.project.grader.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/create/user")
    public UsersRequest createUser(@RequestBody UsersRequest usersRequest){
        return usersService.createUser(usersRequest);
    }

}
