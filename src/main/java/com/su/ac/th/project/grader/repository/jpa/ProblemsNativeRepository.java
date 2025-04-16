package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.model.Projection.ProblemWithStatsProjection;
import com.su.ac.th.project.grader.model.request.problem.ProblemSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProblemsNativeRepository {
    Page<ProblemWithStatsProjection> getProblemsWithStatus(
            Long userId,
            ProblemSearchCriteria searchCriteria,
            Pageable pageable
    );
}
