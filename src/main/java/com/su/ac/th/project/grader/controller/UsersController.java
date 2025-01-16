package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.dto.request.UsersRequest;
import com.su.ac.th.project.grader.model.UsersModel;
import com.su.ac.th.project.grader.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/get/users")
    public List<UsersModel> getUsers(){
        return usersService.getAllUsers();
    }

    @PostMapping("/create/user")
    public UsersRequest createUser(@RequestBody UsersRequest usersRequest){
        return usersService.createUser(usersRequest);
    }

    @PutMapping("/update/user")
    public UsersRequest updateUser(@RequestBody UsersRequest usersRequest){
        return usersService.updateUser(usersRequest);
    }

    @DeleteMapping("/delete/user")
    public void deleteUser(@RequestBody UsersRequest usersRequest){
        usersService.deleteUser(usersRequest);
    }

}
