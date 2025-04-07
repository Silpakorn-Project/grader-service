package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.entity.ProblemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProblemsRepository extends JpaRepository<ProblemsEntity, Long>, JpaSpecificationExecutor<ProblemsEntity> {
}
