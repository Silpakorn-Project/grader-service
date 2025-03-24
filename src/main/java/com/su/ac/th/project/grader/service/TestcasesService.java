package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.TestcasesEntity;
import com.su.ac.th.project.grader.exception.testcase.TestCaseNotFoundException;
import com.su.ac.th.project.grader.exception.testcase.TestCasesNotFoundForProblemIdException;
import com.su.ac.th.project.grader.model.PaginationResponse;
import com.su.ac.th.project.grader.model.request.testcase.TestcasesRequest;
import com.su.ac.th.project.grader.model.request.testcase.TestcasesUpdateRequest;
import com.su.ac.th.project.grader.model.response.TestcasesResponse;
import com.su.ac.th.project.grader.repository.jpa.TestcasesRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
            Long problemId, Integer offset, Integer limit, String sortBy, String sortType) {

        if (isPaginationValid(offset, limit)) {
            Pageable pageable = createPageable(offset, limit, sortBy, sortType);
            Page<TestcasesEntity> testcasesEntityPage = fetchTestCases(problemId, pageable);
            return createTestcasesResponse(testcasesEntityPage);
        }

        List<TestcasesEntity> testcasesEntityList = fetchTestCases(problemId);
        return createTestcasesResponse(testcasesEntityList);
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
        TestcasesEntity testcasesEntity = DtoEntityMapper.mapToEntity(testcasesRequest, TestcasesEntity.class);
        testcasesRepository.save(testcasesEntity);

        return 1;
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

    private PaginationResponse<TestcasesResponse> createTestcasesResponse(Page<TestcasesEntity> testcasesPage) {
        List<TestcasesResponse> testcasesResponses = DtoEntityMapper.mapListToDto(testcasesPage.getContent(), TestcasesResponse.class);

        PaginationResponse<TestcasesResponse> response = new PaginationResponse<>();
        response.setData(testcasesResponses);
        response.setTotalRecords(testcasesPage.getTotalElements());
        response.setTotalPages(testcasesPage.getTotalPages());

        return response;
    }

    private PaginationResponse<TestcasesResponse> createTestcasesResponse(List<TestcasesEntity> testcasesList) {
        List<TestcasesResponse> testcasesResponses = DtoEntityMapper.mapListToDto(testcasesList, TestcasesResponse.class);

        PaginationResponse<TestcasesResponse> response = new PaginationResponse<>();
        response.setData(testcasesResponses);
        response.setTotalRecords((long) testcasesList.size());
        response.setTotalPages(1);

        return response;
    }

    private boolean isPaginationValid(Integer offset, Integer limit) {
        return offset != null && offset > 0 && limit != null && limit > 0;
    }

    private Pageable createPageable(Integer offset, Integer limit, String sortBy, String sortType) {
        return PageRequest.of(offset - 1, limit, Sort.by(getSortDirection(sortType), sortBy));
    }

    private Sort.Direction getSortDirection(String sortType) {
        return "asc".equalsIgnoreCase(sortType) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
