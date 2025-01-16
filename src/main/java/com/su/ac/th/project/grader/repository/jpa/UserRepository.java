package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
