package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.constant.HttpConstant;
import com.su.ac.th.project.grader.model.BaseResponseModel;
import com.su.ac.th.project.grader.model.request.ProblemRequest;
import com.su.ac.th.project.grader.model.request.ProblemUpdateRequest;
import com.su.ac.th.project.grader.service.ProblemsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@RestController
@RequestMapping("/api/problems")
public class ProblemsController {

    private final ProblemsService problemsService;

    public ProblemsController(ProblemsService problemsService) {
        this.problemsService = problemsService;
    }

    @GetMapping("/")
    public ResponseEntity<BaseResponseModel> getAllProblems() {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(problemsService.getAllProblems())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModel> getProblemById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(problemsService.getProblemById(id))
                .build());
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponseModel> createProblem(
            @Valid @RequestBody ProblemRequest problemRequest
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(problemsService.createProblem(problemRequest))
                .build());
    }

    @PutMapping("/")
    public ResponseEntity<BaseResponseModel> updateProblem(
            @Valid @RequestBody ProblemUpdateRequest problemUpdateRequest
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(problemsService.updateProblem(problemUpdateRequest))
                .build());
    }

    @DeleteMapping("/")
    public ResponseEntity<BaseResponseModel> deleteProblemById(
            @Valid @RequestBody ProblemUpdateRequest problemUpdateRequest
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(problemsService.deleteProblemById(problemUpdateRequest))
                .build());
    }


}
