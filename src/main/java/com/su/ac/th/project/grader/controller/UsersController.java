package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.constant.HttpConstant;
import com.su.ac.th.project.grader.model.BaseResponseModel;
import com.su.ac.th.project.grader.model.request.UsersRequest;
import com.su.ac.th.project.grader.model.request.UsersUpdateRequest;
import com.su.ac.th.project.grader.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@RestController
@RequestMapping("/api")
@Slf4j
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "Get all users")
    @GetMapping("/get/users")
    public ResponseEntity<BaseResponseModel> getUsers() {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .message(HttpConstant.Message.SUCCESS)
                .code(HttpConstant.Status.SUCCESS)
                .data(usersService.getAllUsers())
                .build());
    }

    @PostMapping("/create/user")
    public ResponseEntity<BaseResponseModel> createUser(@Valid @RequestBody UsersRequest usersRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .message(HttpConstant.Message.SUCCESS)
                .code(HttpConstant.Status.SUCCESS)
                .data(usersService.createUser(usersRequest))
                .build());
    }

    @PutMapping("/update/user")
    public ResponseEntity<BaseResponseModel> updateUser(@Valid @RequestBody UsersUpdateRequest usersUpdateRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .message(HttpConstant.Message.SUCCESS)
                .code(HttpConstant.Status.SUCCESS)
                .data(usersService.updateUser(usersUpdateRequest))
                .build());
    }

    @DeleteMapping("/delete/user")
    public ResponseEntity<BaseResponseModel> deleteUser(@Valid @RequestBody UsersRequest usersRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .message(HttpConstant.Message.SUCCESS)
                .code(HttpConstant.Status.SUCCESS)
                .data(usersService.deleteUser(usersRequest))
                .build());
    }
}
