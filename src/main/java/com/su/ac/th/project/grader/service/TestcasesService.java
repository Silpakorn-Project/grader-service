package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.entity.TestcasesEntity;
import com.su.ac.th.project.grader.exception.testcase.TestCaseNotFoundException;
import com.su.ac.th.project.grader.model.request.testcase.TestcasesRequest;
import com.su.ac.th.project.grader.model.request.testcase.TestcasesUpdateRequest;
import com.su.ac.th.project.grader.model.response.TestcasesResponse;
import com.su.ac.th.project.grader.repository.jpa.TestcasesRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TestcasesService {

    private final TestcasesRepository testcasesRepository;

    public TestcasesService(TestcasesRepository testcasesRepository) {
        this.testcasesRepository = testcasesRepository;
    }

    public List<TestcasesResponse> getAllTestcases() {
        List<TestcasesEntity> testcasesEntityList = testcasesRepository.findAll();
        return DtoEntityMapper.mapListToDto(testcasesEntityList, TestcasesResponse.class);
    }

    public TestcasesResponse getTestcasesById(Long id) {
        TestcasesEntity testcasesEntity = testcasesRepository
                .findById(id)
                .orElseThrow(() -> new TestCaseNotFoundException(id));
        return DtoEntityMapper.mapToDto(testcasesEntity, TestcasesResponse.class);
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
}
