package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.constant.HttpConstant;
import com.su.ac.th.project.grader.model.BaseResponseModel;
import com.su.ac.th.project.grader.model.request.SubmissionsRequest;
import com.su.ac.th.project.grader.model.request.SubmissionsUpdateRequest;
import com.su.ac.th.project.grader.model.request.SubmitRequest;
import com.su.ac.th.project.grader.service.SubmissionsService;
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

    @GetMapping("/")
    public ResponseEntity<BaseResponseModel> getAllSubmissions() {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.getAllSubmissions())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModel> getSubmissionById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.getSubmissionById(id))
                .build());
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponseModel> createSubmission(@RequestBody SubmissionsRequest submissionsRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.createSubmission(submissionsRequest))
                .build());
    }

    @PutMapping("/")
    public ResponseEntity<BaseResponseModel> updateSubmission(@RequestBody SubmissionsUpdateRequest submissionsUpdateRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.updateSubmission(submissionsUpdateRequest))
                .build());
    }

    @DeleteMapping("/")
    public ResponseEntity<BaseResponseModel> deleteSubmissionById(@RequestBody SubmissionsUpdateRequest submissionsUpdateRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.deleteSubmissionById(submissionsUpdateRequest))
                .build());
    }

    @PostMapping("/submit")
    public ResponseEntity<BaseResponseModel> submit(@RequestBody SubmitRequest submitRequest) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(submissionsService.submit(submitRequest))
                .build());
    }
}
