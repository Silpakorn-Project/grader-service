package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.constant.CommonConstant.*;
import com.su.ac.th.project.grader.entity.ProblemsEntity;
import com.su.ac.th.project.grader.exception.problem.ProblemNotFoundException;
import com.su.ac.th.project.grader.model.request.ProblemRequest;
import com.su.ac.th.project.grader.model.request.ProblemUpdateRequest;
import com.su.ac.th.project.grader.model.response.ProblemsResponse;
import com.su.ac.th.project.grader.repository.jpa.ProblemsRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProblemsService {

    private final ProblemsRepository problemsRepository;

    public ProblemsService(ProblemsRepository problemsRepository) {
        this.problemsRepository = problemsRepository;
    }

    public List<ProblemsResponse> getAllProblems() {

        List<ProblemsEntity> problemsEntityList = problemsRepository.findAll();
        return DtoEntityMapper.mapListToDto(problemsEntityList, ProblemsResponse.class);

    }

    public ProblemsResponse getProblemById(Long id) {

        ProblemsEntity problemsEntity = problemsRepository
                .findById(id)
                .orElseThrow(() -> new ProblemNotFoundException(id));

        return DtoEntityMapper.mapToDto(problemsEntity, ProblemsResponse.class);
    }

    public int createProblem(ProblemRequest problemRequest) {

        ProblemsEntity problemsEntity = DtoEntityMapper.mapToEntity(problemRequest, ProblemsEntity.class);
        problemsRepository.save(problemsEntity);

        return 1;
    }

    public int updateProblem(ProblemUpdateRequest problemUpdateRequest) {
        int rowUpdated = 0;
        ProblemsEntity problemsEntity = problemsRepository
                .findById(problemUpdateRequest.getProblemId())
                .orElseThrow(() -> new ProblemNotFoundException(problemUpdateRequest.getProblemId()));

        if (problemsEntity.getTitle() != null) {
            problemsEntity.setTitle(problemUpdateRequest.getTitle());
            rowUpdated += 1;
        }

        if (problemsEntity.getDescription() != null) {
            problemsEntity.setDescription(problemUpdateRequest.getDescription());
            rowUpdated += 1;
        }

        if (problemUpdateRequest.getDifficulty() != null) {
            problemsEntity.setDifficulty(ProblemDifficulty.valueOf(problemUpdateRequest.getDifficulty()));
            rowUpdated += 1;
        }

        if (problemUpdateRequest.getType() != null) {
            problemsEntity.setType(ProblemType.valueOf(problemUpdateRequest.getType()));
            rowUpdated += 1;
        }

        problemsEntity.setUpdatedAt(LocalDateTime.now());

        problemsRepository.save(problemsEntity);
        return rowUpdated;
    }

    public Object deleteProblemById(Long id) {
        problemsRepository.deleteById(id);
        return null;
    }
}
