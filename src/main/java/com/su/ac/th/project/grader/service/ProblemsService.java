package com.su.ac.th.project.grader.service;

import com.su.ac.th.project.grader.constant.CommonConstant.*;
import com.su.ac.th.project.grader.entity.ProblemsEntity;
import com.su.ac.th.project.grader.exception.problem.ProblemNotFoundException;
import com.su.ac.th.project.grader.model.PaginationRequest;
import com.su.ac.th.project.grader.model.PaginationResponse;
import com.su.ac.th.project.grader.model.Projection.ProblemWithStatsProjection;
import com.su.ac.th.project.grader.model.request.problem.ProblemRequest;
import com.su.ac.th.project.grader.model.request.problem.ProblemSearchCriteria;
import com.su.ac.th.project.grader.model.request.problem.ProblemUpdateRequest;
import com.su.ac.th.project.grader.model.response.ProblemsResponse;
import com.su.ac.th.project.grader.repository.jpa.ProblemsNativeRepository;
import com.su.ac.th.project.grader.repository.jpa.ProblemsRepository;
import com.su.ac.th.project.grader.repository.jpa.spefication.ProblemsSpecification;
import com.su.ac.th.project.grader.util.DtoEntityMapper;
import com.su.ac.th.project.grader.util.PaginationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.ParsingUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemsService {

    private final ProblemsRepository problemsRepository;
    private final ProblemsNativeRepository problemsNativeRepository;

    public PaginationResponse<ProblemsResponse> getAllProblems(
            PaginationRequest paginationRequest,
            ProblemSearchCriteria searchCriteria,
            Long userId
    ) {
        if (userId != null) {
            Sort nativeSort = paginationRequest.getSortBy() != null && paginationRequest.getSortType() != null
                    ? Sort.by(paginationRequest.getSortType(), ParsingUtils.reconcatenateCamelCase(paginationRequest.getSortBy(), "_"))
                    : Sort.unsorted();

            Pageable nativePageable = PaginationUtil.createPageable(
                    paginationRequest.getOffset(),
                    paginationRequest.getLimit(),
                    nativeSort
            );

            Page<ProblemWithStatsProjection> projectedPage = problemsNativeRepository.getProblemsWithStatus(
                    userId,
                    searchCriteria,
                    nativePageable
            );

            return PaginationUtil.createPaginationResponse(
                    projectedPage, ProblemsResponse.class
            );
        }

        Sort sort = paginationRequest.getSortBy() != null && paginationRequest.getSortType() != null
                ? Sort.by(paginationRequest.getSortType(), paginationRequest.getSortBy())
                : Sort.unsorted();

        Specification<ProblemsEntity> spec = Specification
                .where(ProblemsSpecification.hasTitle(searchCriteria.getTitle()))
                .and(ProblemsSpecification.hasDescription(searchCriteria.getDescription()))
                .and(ProblemsSpecification.hasDifficulty(searchCriteria.getDifficulty()))
                .and(ProblemsSpecification.hasType(searchCriteria.getType()));

        if (PaginationUtil.isPaginationValid(paginationRequest.getOffset(), paginationRequest.getLimit())) {
            Pageable pageable = PaginationUtil.createPageable(
                    paginationRequest.getOffset(),
                    paginationRequest.getLimit(),
                    sort
            );

            Page<ProblemsEntity> problemsPage = problemsRepository.findAll(spec, pageable);
            return PaginationUtil.createPaginationResponse(problemsPage, ProblemsResponse.class);
        }

        List<ProblemsEntity> problemsList = problemsRepository.findAll(spec, sort);

        return PaginationUtil.createPaginationResponse(problemsList, ProblemsResponse.class);
    }

    public ProblemsResponse getProblemById(Long id) {

        ProblemsEntity problemsEntity = problemsRepository
                .findById(id)
                .orElseThrow(() -> new ProblemNotFoundException(id));

        return DtoEntityMapper.mapToDto(problemsEntity, ProblemsResponse.class);
    }

    public Long createProblem(ProblemRequest problemRequest) {

        ProblemsEntity problemsEntity = DtoEntityMapper.mapToEntity(problemRequest, ProblemsEntity.class);
        problemsRepository.save(problemsEntity);

        return problemsEntity.getProblemId();
    }

    public int updateProblem(ProblemUpdateRequest problemUpdateRequest, Long id) {
        int rowUpdated = 0;
        ProblemsEntity problemsEntity = problemsRepository
                .findById(id)
                .orElseThrow(() -> new ProblemNotFoundException(id));

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

    public Long getRandomProblems() {
        ProblemsResponse problemsResponse = DtoEntityMapper.mapToDto(
                problemsRepository.getRandomProblems(), ProblemsResponse.class);
        return problemsResponse.getProblemId();
    }
}
