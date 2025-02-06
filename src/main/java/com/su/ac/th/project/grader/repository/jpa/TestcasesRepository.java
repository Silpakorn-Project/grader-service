package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.entity.TestcasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestcasesRepository extends JpaRepository<TestcasesEntity, Long> {
    List<TestcasesEntity> findByProblemId(Long problemId);
}
