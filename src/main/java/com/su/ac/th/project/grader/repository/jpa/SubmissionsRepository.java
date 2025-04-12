package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.constant.CommonConstant;
import com.su.ac.th.project.grader.entity.SubmissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubmissionsRepository extends JpaRepository<SubmissionsEntity, Long>, JpaSpecificationExecutor<SubmissionsEntity> {
    boolean existsByUserIdAndProblemIdAndStatus(Long userId, Long problemId, CommonConstant.Status status);
}
