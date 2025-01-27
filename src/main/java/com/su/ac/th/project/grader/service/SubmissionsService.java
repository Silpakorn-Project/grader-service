package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.SubmissionsEntity;
import com.su.ac.th.project.grader.model.request.SubmissionsRequest;
import com.su.ac.th.project.grader.model.request.SubmissionsUpdateRequest;
import com.su.ac.th.project.grader.model.request.SubmitRequest;
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

import static com.su.ac.th.project.grader.exception.BusinessException.notFound;

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
                .orElseThrow(() -> notFound(String.valueOf(id)));

        return DtoEntityMapper.mapToDto(submissionsEntity, SubmissionsResponse.class);
    }

    public int createSubmission(SubmissionsRequest submissionsRequest) {

        SubmissionsEntity submissionsEntity = DtoEntityMapper.mapToEntity(submissionsRequest, SubmissionsEntity.class);
        submissionsRepository.save(submissionsEntity);

        return 1;
    }

    public int updateSubmission(SubmissionsUpdateRequest submissionsUpdateRequest) {

        int rowUpdated = 0;
        SubmissionsEntity submissionsEntity = submissionsRepository
                .findById(submissionsUpdateRequest.getProblemId())
                .orElseThrow(() -> notFound(String.valueOf(submissionsUpdateRequest.getProblemId())));

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
            submissionsEntity.setLanguage(
                    switch (submissionsUpdateRequest.getLanguage()) {
                        case "C" -> SubmissionsEntity.Language.C;
                        case "JAVA" -> SubmissionsEntity.Language.JAVA;
                        case "PYTHON" -> SubmissionsEntity.Language.PYTHON;
                        default -> throw new RuntimeException("Invalid language");
                    }
            );
            rowUpdated += 1;
        }

        if (!Objects.isNull(submissionsUpdateRequest.getStatus())) {
            submissionsEntity.setStatus(
                    switch (submissionsUpdateRequest.getStatus()) {
                        case "Pending" -> SubmissionsEntity.Status.Pending;
                        case "Passed" -> SubmissionsEntity.Status.Passed;
                        case "Failed" -> SubmissionsEntity.Status.Failed;
                        default -> throw new RuntimeException("Invalid status");
                    }
            );
            rowUpdated += 1;
        }
        submissionsEntity.setUpdatedAt(LocalDateTime.now());

        submissionsRepository.save(submissionsEntity);
        return rowUpdated;
    }

    public Object deleteSubmissionById(SubmissionsUpdateRequest submissionsUpdateRequest) {
        if (Objects.isNull(submissionsUpdateRequest.getSubmissionId())) {
            throw new RuntimeException("submission cannot be null");
        }
        submissionsRepository.deleteById(submissionsUpdateRequest.getSubmissionId());
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
            SubmissionsEntity.Status status = response.isPassed() ?
                    SubmissionsEntity.Status.Passed : SubmissionsEntity.Status.Failed;

            submissionsEntity.setStatus(status);
            submissionsEntity.setScorePercent(scorePercent);

            submissionsRepository.save(submissionsEntity);
        }

        return response;
    }
}
