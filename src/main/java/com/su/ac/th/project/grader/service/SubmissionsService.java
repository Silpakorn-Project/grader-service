package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.constant.CommonConstant.*;
import com.su.ac.th.project.grader.entity.SubmissionsEntity;
import com.su.ac.th.project.grader.model.request.SubmissionsRequest;
import com.su.ac.th.project.grader.model.request.SubmissionsUpdateRequest;
import com.su.ac.th.project.grader.model.response.SubmissionsResponse;
import com.su.ac.th.project.grader.repository.jpa.SubmissionsRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.su.ac.th.project.grader.exception.BusinessException.notFound;

@Service
public class SubmissionsService {

    private final SubmissionsRepository submissionsRepository;

    public SubmissionsService(SubmissionsRepository submissionsRepository) {
        this.submissionsRepository = submissionsRepository;
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
                        case "C" -> Language.C;
                        case "JAVA" -> Language.JAVA;
                        case "PYTHON" -> Language.PYTHON;
                        default -> throw new RuntimeException("Invalid language");
                    }
            );
            rowUpdated += 1;
        }

        if (!Objects.isNull(submissionsUpdateRequest.getStatus())) {
            submissionsEntity.setStatus(
                    switch (submissionsUpdateRequest.getStatus()) {
                        case "Pending" -> Status.Pending;
                        case "Passed" -> Status.Passed;
                        case "Failed" -> Status.Failed;
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

}
