package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.Model.BaseResponseModel;
import com.su.ac.th.project.grader.Model.request.UsersRequest;
import com.su.ac.th.project.grader.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@Slf4j
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/get/users")
    public BaseResponseModel getUsers(){
        return BaseResponseModel.builder()
                .timestamp(LocalDateTime.now())
                .message("Get all users")
                .code("200")
                .data(usersService.getAllUsers())
                .build();
    }

    @PostMapping("/create/user")
    public BaseResponseModel createUser(@RequestBody UsersRequest usersRequest){
        return BaseResponseModel.builder()
                .timestamp(LocalDateTime.now())
                .message("Create User Sucessfully")
                .code("200")
                .data(usersService.createUser(usersRequest))
                .build();
    }

    @PutMapping("/update/user")
    public BaseResponseModel updateUser(@RequestBody UsersRequest usersRequest){
        return BaseResponseModel.builder()
                .timestamp(LocalDateTime.now())
                .message("Update Successfully")
                .code("200")
                .data(usersService.updateUser(usersRequest))
                .build();
    }

    @DeleteMapping("/delete/user")
    public BaseResponseModel deleteUser(@RequestBody UsersRequest usersRequest){
        return BaseResponseModel.builder()
                .timestamp(LocalDateTime.now())
                .message("Delete Successfully")
                .code("200")
                .data(usersRequest.getId())
                .build();
    }
}
