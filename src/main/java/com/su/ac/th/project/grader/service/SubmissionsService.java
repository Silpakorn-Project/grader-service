package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.constant.CommonConstant;
import com.su.ac.th.project.grader.constant.CommonConstant.*;
import com.su.ac.th.project.grader.entity.SubmissionsEntity;
import com.su.ac.th.project.grader.exception.submission.SubmissionNotFoundException;
import com.su.ac.th.project.grader.model.request.SubmitRequest;
import com.su.ac.th.project.grader.model.request.submission.SubmissionsRequest;
import com.su.ac.th.project.grader.model.request.submission.SubmissionsUpdateRequest;
import com.su.ac.th.project.grader.model.response.SubmissionsResponse;
import com.su.ac.th.project.grader.model.response.TestcasesResponse;
import com.su.ac.th.project.grader.repository.jpa.SubmissionsRepository;
import com.su.ac.th.project.grader.service.external.SandboxClient;
import com.su.ac.th.project.grader.service.external.request.RunTestRequest;
import com.su.ac.th.project.grader.service.external.request.TestCase;
import com.su.ac.th.project.grader.service.external.response.RunTestResponse;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SubmissionsService {

    private final SubmissionsRepository submissionsRepository;
    private final SandboxClient sandboxClient;
    private final TestcasesService testcasesService;

    public SubmissionsService(SubmissionsRepository submissionsRepository, SandboxClient sandboxClient, TestcasesService testcasesService) {
        this.submissionsRepository = submissionsRepository;
        this.sandboxClient = sandboxClient;
        this.testcasesService = testcasesService;
    }

    public List<SubmissionsResponse> getAllSubmissions() {

        List<SubmissionsEntity> submissionsEntityList = submissionsRepository.findAll();
        return DtoEntityMapper.mapListToDto(submissionsEntityList, SubmissionsResponse.class);
    }

    public SubmissionsResponse getSubmissionById(Long id) {

        SubmissionsEntity submissionsEntity = submissionsRepository
                .findById(id)
                .orElseThrow(() -> new SubmissionNotFoundException(id));

        return DtoEntityMapper.mapToDto(submissionsEntity, SubmissionsResponse.class);
    }

    public int createSubmission(SubmissionsRequest submissionsRequest) {

        SubmissionsEntity submissionsEntity = DtoEntityMapper.mapToEntity(submissionsRequest, SubmissionsEntity.class);
        submissionsRepository.save(submissionsEntity);

        return 1;
    }

    public int updateSubmission(SubmissionsUpdateRequest submissionsUpdateRequest, Long id) {
        int rowUpdated = 0;
        SubmissionsEntity submissionsEntity = submissionsRepository
                .findById(id)
                .orElseThrow(() -> new SubmissionNotFoundException(id));

        if (!Objects.isNull(submissionsUpdateRequest.getUserId())) {
            submissionsEntity.setUserId(submissionsUpdateRequest.getUserId());
            rowUpdated += 1;
        }

        if (!Objects.isNull(submissionsUpdateRequest.getProblemId())) {
            submissionsEntity.setProblemId(submissionsUpdateRequest.getProblemId());
            rowUpdated += 1;
        }

        if (!Objects.isNull(submissionsUpdateRequest.getCode())) {
            submissionsEntity.setCode(submissionsUpdateRequest.getCode());
            rowUpdated += 1;
        }

        if (!Objects.isNull(submissionsUpdateRequest.getLanguage())) {
            submissionsEntity.setLanguage(Language.valueOf(submissionsUpdateRequest.getLanguage()));
            rowUpdated += 1;
        }

        if (!Objects.isNull(submissionsUpdateRequest.getStatus())) {
            submissionsEntity.setStatus(Status.valueOf(submissionsUpdateRequest.getStatus()));
            rowUpdated += 1;
        }
        submissionsEntity.setUpdatedAt(LocalDateTime.now());

        submissionsRepository.save(submissionsEntity);
        return rowUpdated;
    }

    public Object deleteSubmissionById(Long id) {
        submissionsRepository.deleteById(id);
        return null;
    }

    public RunTestResponse submit(SubmitRequest submitRequest) {

        List<TestcasesResponse> testCases = testcasesService.getTestcasesByProblemId(submitRequest.getProblemId());

        List<TestCase> testCasesRequest = testCases.stream().map(testCase -> new TestCase(testCase.getInputData(), testCase.getExpectedOutput())).toList();

        RunTestRequest runTestRequest = new RunTestRequest(submitRequest.getCode(), testCasesRequest);
        RunTestResponse response = sandboxClient.runTests(runTestRequest, submitRequest.getLanguage()).block();

        if (response != null) {
            SubmissionsEntity submissionsEntity = DtoEntityMapper.mapToEntity(submitRequest, SubmissionsEntity.class);

            BigDecimal scorePercent = BigDecimal
                    .valueOf((double) response.getTestcase_passed() / response.getTestcase_total() * 100)
                    .setScale(2, RoundingMode.HALF_UP);
            CommonConstant.Status status = response.isPassed() ?
                    CommonConstant.Status.Passed : CommonConstant.Status.Failed;

            submissionsEntity.setStatus(status);
            submissionsEntity.setScorePercent(scorePercent);

            submissionsRepository.save(submissionsEntity);
        }

        return response;
    }
}
