package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.constant.CommonConstant;
import com.su.ac.th.project.grader.constant.CommonConstant.*;
import com.su.ac.th.project.grader.entity.SubmissionsEntity;
import com.su.ac.th.project.grader.exception.submission.SubmissionNotFoundException;
import com.su.ac.th.project.grader.model.PaginationRequest;
import com.su.ac.th.project.grader.model.PaginationResponse;
import com.su.ac.th.project.grader.model.request.submission.*;
import com.su.ac.th.project.grader.model.response.SubmissionsResponse;
import com.su.ac.th.project.grader.repository.jpa.SubmissionsRepository;
import com.su.ac.th.project.grader.model.request.sandbox.RunTestRequest;
import com.su.ac.th.project.grader.model.request.sandbox.TestCase;
import com.su.ac.th.project.grader.model.response.RunTestResponse;
import com.su.ac.th.project.grader.repository.jpa.spefication.SubmissionsSpecification;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import com.su.ac.th.project.grader.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class SubmissionsService {

    private final SubmissionsRepository submissionsRepository;
    private final SandboxClientService sandboxClient;
    private final TestcasesService testcasesService;
    private final UsersService usersService;

    public SubmissionsService(SubmissionsRepository submissionsRepository, SandboxClientService sandboxClient, TestcasesService testcasesService, UsersService usersService) {
        this.submissionsRepository = submissionsRepository;
        this.sandboxClient = sandboxClient;
        this.testcasesService = testcasesService;
        this.usersService = usersService;
    }

    public PaginationResponse<SubmissionsResponse> getSubmissions(
            PaginationRequest paginationRequest, SubmissionsSearchCriteria searchCriteria
    ) {
        Specification<SubmissionsEntity> spec = Specification
                .where(SubmissionsSpecification.hasUserId(searchCriteria.getUserId()))
                .and(SubmissionsSpecification.hasProblemId(searchCriteria.getProblemId()))
                .and(SubmissionsSpecification.hasLanguage(searchCriteria.getLanguage()))
                .and(SubmissionsSpecification.hasStatus(searchCriteria.getStatus()));

        Sort sort = paginationRequest.getSortBy() != null && paginationRequest.getSortType() != null
                ? Sort.by(paginationRequest.getSortType(), paginationRequest.getSortBy())
                : Sort.unsorted();

        if (PaginationUtil.isPaginationValid(paginationRequest.getOffset(), paginationRequest.getLimit())) {
            Pageable pageable = PaginationUtil.createPageable(
                    paginationRequest.getOffset(),
                    paginationRequest.getLimit(),
                    sort);

            Page<SubmissionsEntity> submissionsPage = submissionsRepository.findAll(spec, pageable);
            return PaginationUtil.createPaginationResponse(submissionsPage, SubmissionsResponse.class);
        }

        List<SubmissionsEntity> submissionsEntityList = submissionsRepository.findAll(spec, sort);
        return PaginationUtil.createPaginationResponse(submissionsEntityList, SubmissionsResponse.class);
    }

    private PaginationResponse<SubmissionsResponse> createTestcasesResponse(Page<SubmissionsEntity> submissionsPage) {
        List<SubmissionsResponse> submissionsResponses = DtoEntityMapper.mapListToDto(submissionsPage.getContent(), SubmissionsResponse.class);

        PaginationResponse<SubmissionsResponse> response = new PaginationResponse<>();
        response.setData(submissionsResponses);
        response.setTotalRecords(submissionsPage.getTotalElements());
        response.setTotalPages(submissionsPage.getTotalPages());

        return response;
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

        // Check if user exist
        usersService.getUserById(submitRequest.getUserId());

        List<TestCase> testCases = testcasesService.getTestcasesByProblemId(submitRequest.getProblemId())
                .stream()
                .map(TestCase::new)
                .toList();

        RunTestRequest runTestRequest = new RunTestRequest(submitRequest.getCode(), testCases);
        RunTestResponse response = sandboxClient.runTests(runTestRequest, submitRequest.getLanguage());

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
