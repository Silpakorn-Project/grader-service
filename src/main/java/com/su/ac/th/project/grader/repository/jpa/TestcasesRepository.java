package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.entity.TestcasesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestcasesRepository extends JpaRepository<TestcasesEntity, Long> {
}
