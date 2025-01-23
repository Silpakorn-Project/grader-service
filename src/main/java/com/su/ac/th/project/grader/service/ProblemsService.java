package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.constant.CommonConstant.*;
import com.su.ac.th.project.grader.entity.ProblemsEntity;
import com.su.ac.th.project.grader.model.request.ProblemRequest;
import com.su.ac.th.project.grader.model.request.ProblemUpdateRequest;
import com.su.ac.th.project.grader.model.response.ProblemsResponse;
import com.su.ac.th.project.grader.repository.jpa.ProblemsRepository;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.su.ac.th.project.grader.exception.BusinessException.notFound;

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
                .orElseThrow(() -> notFound(String.valueOf(id)));

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
                .orElseThrow(() -> notFound(String.valueOf(problemUpdateRequest.getProblemId())));

        if (problemsEntity.getTitle() != null) {
            problemsEntity.setTitle(problemUpdateRequest.getTitle());
            rowUpdated += 1;
        }

        if (problemsEntity.getDescription() != null) {
            problemsEntity.setDescription(problemUpdateRequest.getDescription());
            rowUpdated += 1;
        }

        if (problemUpdateRequest.getDifficulty() != null) {
            problemsEntity.setDifficulty(
                    switch (problemUpdateRequest.getDifficulty()) {
                        case "EASY" -> ProblemDifficulty.EASY;
                        case "MEDIUM" -> ProblemDifficulty.MEDIUM;
                        case "HARD" -> ProblemDifficulty.HARD;
                        default -> throw new RuntimeException("Invalid difficulty");
                    }
            );
            rowUpdated += 1;
        }

        if (problemUpdateRequest.getType() != null) {
            problemsEntity.setType(
                    switch (problemUpdateRequest.getType()) {
                        case "MATH" -> ProblemType.MATH;
                        case "STRING" -> ProblemType.STRING;
                        case "DATA_STRUCTURE" -> ProblemType.DATA_STRUCTURE;
                        case "GRAPH" -> ProblemType.GRAPH;
                        default -> throw new RuntimeException("Invalid type");
                    }
            );
            rowUpdated += 1;
        }

        problemsEntity.setUpdatedAt(LocalDateTime.now());

        problemsRepository.save(problemsEntity);
        return rowUpdated;
    }

    public Object deleteProblemById(ProblemUpdateRequest problemUpdateRequest) {

        if (Objects.isNull(problemUpdateRequest.getProblemId())) {
            throw new RuntimeException("problemId cannot be null");
        }
        problemsRepository.deleteById(problemUpdateRequest.getProblemId());
        return null;
    }
}
