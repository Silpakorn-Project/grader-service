package com.su.ac.th.project.grader.controller;

import com.su.ac.th.project.grader.constant.HttpConstant;
import com.su.ac.th.project.grader.model.BaseResponseModel;
import com.su.ac.th.project.grader.model.request.testcase.TestcasesRequest;
import com.su.ac.th.project.grader.model.request.testcase.TestcasesUpdateRequest;
import com.su.ac.th.project.grader.service.TestcasesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.su.ac.th.project.grader.util.CommonUtil.getDateTimeNow;

@RestController
@RequestMapping("/api/testcases")
public class TestcasesController {

    private final TestcasesService testcasesService;

    public TestcasesController(TestcasesService testcasesService) {
        this.testcasesService = testcasesService;
    }

    @GetMapping("/")
    public ResponseEntity<BaseResponseModel> getAllTestCases() {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(testcasesService.getAllTestcases())
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponseModel> getTestCaseById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(testcasesService.getTestcasesById(id))
                .build());
    }

    @PostMapping("/")
    public ResponseEntity<BaseResponseModel> createTestCase(
            @Valid @RequestBody TestcasesRequest testcasesRequest
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(testcasesService.createTestcase(testcasesRequest))
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponseModel> updateTestCase(
            @Valid @RequestBody TestcasesUpdateRequest testcasesUpdateRequest,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(testcasesService.updateTestcases(testcasesUpdateRequest, id))
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponseModel> deleteProblemById(
            @PathVariable() Long id
    ) {
        return ResponseEntity.ok(BaseResponseModel.builder()
                .timestamp(getDateTimeNow())
                .code(HttpConstant.Status.SUCCESS)
                .message(HttpConstant.Message.SUCCESS)
                .data(testcasesService.deleteTestcasesById(id))
                .build());
    }

}
