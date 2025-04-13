package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.constant.HttpConstant;
import com.su.ac.th.project.grader.model.BaseResponseModel;
import com.su.ac.th.project.grader.model.PaginationRequest;
import com.su.ac.th.project.grader.model.PaginationResponse;
import com.su.ac.th.project.grader.model.request.problem.ProblemRequest;
import com.su.ac.th.project.grader.model.request.problem.ProblemSearchCriteria;
import com.su.ac.th.project.grader.model.request.problem.ProblemUpdateRequest;
import com.su.ac.th.project.grader.model.response.ProblemsResponse;
import com.su.ac.th.project.grader.service.ProblemsService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@RestController
@RequestMapping("/api/problems")
public class ProblemsController {

    private final ProblemsService problemsService;

    public ProblemsController(ProblemsService problemsService) {
        this.problemsService = problemsService;
    }

    @GetMapping()
    public ResponseEntity<BaseResponseModel> getAllProblems(
            @ParameterObject PaginationRequest paginationRequest,
            @ParameterObject ProblemSearchCriteria searchCriteria
    ) {
        PaginationResponse<ProblemsResponse> response = problemsService.getAllProblems(paginationRequest, searchCriteria);

        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .offset(paginationRequest.getOffset())
                .limit(paginationRequest.getLimit())
                .totalRecords(response.getTotalRecords())
                .totalPages(response.getTotalPages())
                .dataCount(response.getData().size())
                .data(response.getData())
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

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
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

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateProblem(
            @Valid @RequestBody ProblemUpdateRequest problemUpdateRequest,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(problemsService.updateProblem(problemUpdateRequest, id))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteProblemById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(problemsService.deleteProblemById(id))
                .build());
    }

    @GetMapping("/randomId")
    public ResponseEntity<BaseResponseModel> getRandomProblems() {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(problemsService.getRandomProblems())
                .build());
    }


}
