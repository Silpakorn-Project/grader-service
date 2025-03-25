package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.constant.HttpConstant;
import com.su.ac.th.project.grader.model.BaseResponseModel;
import com.su.ac.th.project.grader.model.PaginationRequest;
import com.su.ac.th.project.grader.model.PaginationResponse;
import com.su.ac.th.project.grader.model.request.submission.*;
import com.su.ac.th.project.grader.model.response.SubmissionsResponse;
import com.su.ac.th.project.grader.service.SubmissionsService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionsController {

    private final SubmissionsService submissionsService;

    public SubmissionsController(SubmissionsService submissionsService) {
        this.submissionsService = submissionsService;
    }

    @GetMapping()
    public ResponseEntity<BaseResponseModel> getSubmissions(
            @ParameterObject PaginationRequest paginationRequest,
            @ParameterObject SubmissionsSearchCriteria searchCriteria
    ) {
        PaginationResponse<SubmissionsResponse> response = submissionsService.getSubmissions(paginationRequest, searchCriteria);

        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .offset(paginationRequest.getOffset())
                .limit(paginationRequest.getLimit())
                .totalRecords(response.getTotalPages())
                .totalPages(response.getTotalPages())
                .dataCount(response.getData().size())
                .data(response.getData())
                .build());
    }

    @PostMapping()
    public ResponseEntity<BaseResponseModel> createSubmission(
            @Valid @RequestBody SubmissionsRequest submissionsRequest
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.createSubmission(submissionsRequest))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateSubmission(
            @Valid @RequestBody SubmissionsUpdateRequest submissionsUpdateRequest,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.updateSubmission(submissionsUpdateRequest, id))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteSubmissionById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.deleteSubmissionById(id))
                .build());
    }

    @PostMapping("/submit")
    public ResponseEntity<BaseResponseModel> submit(
            @Valid @RequestBody SubmitRequest submitRequest
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.submit(submitRequest))
                .build());
    }
}
