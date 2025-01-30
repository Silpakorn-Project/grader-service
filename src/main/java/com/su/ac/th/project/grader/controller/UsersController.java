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
@RequestMapping("/api/users")
@Slf4j
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "Get all users")
    @GetMapping("/")
    public ResponseEntity<BaseResponseModel> getUsers() {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .message(HttpConstant.Message.SUCCESS)
                .code(HttpConstant.Status.SUCCESS)
                .data(usersService.getAllUsers())
                .build());
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponseModel> createUser(@Valid @RequestBody UsersRequest usersRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .message(HttpConstant.Message.SUCCESS)
                .code(HttpConstant.Status.SUCCESS)
                .data(usersService.createUser(usersRequest))
                .build());
    }

    @PutMapping("/")
    public ResponseEntity<BaseResponseModel> updateUser(@Valid @RequestBody UsersUpdateRequest usersUpdateRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .message(HttpConstant.Message.SUCCESS)
                .code(HttpConstant.Status.SUCCESS)
                .data(usersService.updateUser(usersUpdateRequest))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .message(HttpConstant.Message.SUCCESS)
                .code(HttpConstant.Status.SUCCESS)
                .data(usersService.deleteUser(id))
                .build());
    }
}
