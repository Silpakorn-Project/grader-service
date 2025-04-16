package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.TestcasesEntity;
import com.su.ac.th.project.grader.exception.testcase.TestCaseNotFoundException;
import com.su.ac.th.project.grader.exception.testcase.TestCasesNotFoundForProblemIdException;
import com.su.ac.th.project.grader.model.PaginationRequest;
import com.su.ac.th.project.grader.model.PaginationResponse;
import com.su.ac.th.project.grader.model.request.testcase.TestcasesRequest;
import com.su.ac.th.project.grader.model.request.testcase.TestcasesUpdateRequest;
import com.su.ac.th.project.grader.model.response.TestcasesResponse;
import com.su.ac.th.project.grader.repository.jpa.TestcasesRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import com.su.ac.th.project.grader.util.PaginationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.su.ac.th.project.grader.util.DtoEntityMapper.mapListToDto;

@Service
public class TestcasesService {

    private final TestcasesRepository testcasesRepository;

    public TestcasesService(TestcasesRepository testcasesRepository) {
        this.testcasesRepository = testcasesRepository;
    }

    public PaginationResponse<TestcasesResponse> getTestCases(
            PaginationRequest paginationRequest, Long problemId
    ) {
        if (PaginationUtil.isPaginationValid(paginationRequest.getOffset(), paginationRequest.getLimit())) {
            Pageable pageable = PaginationUtil.createPageable(
                    paginationRequest.getOffset(),
                    paginationRequest.getLimit(),
                    paginationRequest.getSortBy(),
                    paginationRequest.getSortType());

            Page<TestcasesEntity> testcasesEntityPage = fetchTestCases(problemId, pageable);
            return PaginationUtil.createPaginationResponse(testcasesEntityPage, TestcasesResponse.class);
        }

        List<TestcasesEntity> testcasesEntityList = fetchTestCases(problemId);
        return PaginationUtil.createPaginationResponse(testcasesEntityList, TestcasesResponse.class);
    }

    public TestcasesResponse getTestcasesById(Long id) {
        TestcasesEntity testcasesEntity = testcasesRepository
                .findById(id)
                .orElseThrow(() -> new TestCaseNotFoundException(id));
        return DtoEntityMapper.mapToDto(testcasesEntity, TestcasesResponse.class);
    }

    public List<TestcasesResponse> getTestcasesByProblemId(Long id) {
        List<TestcasesEntity> testcasesEntities = testcasesRepository.findByProblemId(id);

        if (testcasesEntities.isEmpty()) {
            throw new TestCasesNotFoundForProblemIdException(id);
        }

        return mapListToDto(testcasesEntities, TestcasesResponse.class);
    }

    public int createTestcase(TestcasesRequest testcasesRequest) {
        List<TestcasesEntity> entities = testcasesRequest.getTestcases().stream()
                .map(tc -> {
                    TestcasesEntity entity = new TestcasesEntity();
                    entity.setProblemId(testcasesRequest.getProblemId());
                    entity.setInputData(tc.getInputData());
                    entity.setExpectedOutput(tc.getExpectedOutput());
                    return entity;
                })
                .toList();

        testcasesRepository.saveAll(entities);
        return entities.size();
    }

    public Object updateTestcases(TestcasesUpdateRequest testcasesUpdateRequest, Long id) {
        int rowUpdated = 0;
        TestcasesEntity testcasesEntity = testcasesRepository
                .findById(id)
                .orElseThrow(() -> new TestCaseNotFoundException(id));

        if (testcasesEntity.getProblemId() != null) {
            testcasesEntity.setProblemId(testcasesUpdateRequest.getProblemId());
            rowUpdated += 1;
        }

        if (testcasesEntity.getInputData() != null) {
            testcasesEntity.setInputData(testcasesUpdateRequest.getInputData());
            rowUpdated += 1;
        }

        if (testcasesEntity.getExpectedOutput() != null) {
            testcasesEntity.setExpectedOutput(testcasesUpdateRequest.getExpectedOutput());
            rowUpdated += 1;
        }

        testcasesEntity.setUpdatedAt(LocalDateTime.now());

        testcasesRepository.save(testcasesEntity);
        return rowUpdated;
    }

    public Object deleteTestcasesById(Long id) {
        testcasesRepository.deleteById(id);
        return null;
    }


    private Page<TestcasesEntity> fetchTestCases(Long problemId, Pageable pageable) {
        return (problemId == null)
                ? testcasesRepository.findAll(pageable)
                : testcasesRepository.findByProblemId(problemId, pageable);
    }

    private List<TestcasesEntity> fetchTestCases(Long problemId) {
        return (problemId == null)
                ? testcasesRepository.findAll()
                : testcasesRepository.findByProblemId(problemId);
    }
}
