package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.entity.SubmissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubmissionsRepository extends JpaRepository<SubmissionsEntity, Long> {
}
