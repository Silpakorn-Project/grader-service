package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.constant.CommonConstant.*;
import com.su.ac.th.project.grader.entity.SubmissionsEntity;
import com.su.ac.th.project.grader.exception.submission.SubmissionNotFoundException;
import com.su.ac.th.project.grader.model.request.SubmissionsRequest;
import com.su.ac.th.project.grader.model.request.SubmissionsUpdateRequest;
import com.su.ac.th.project.grader.model.response.SubmissionsResponse;
import com.su.ac.th.project.grader.repository.jpa.SubmissionsRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
                .orElseThrow(() -> new SubmissionNotFoundException(id));

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
                .orElseThrow(() -> new SubmissionNotFoundException(submissionsUpdateRequest.getSubmissionId()));

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

}
