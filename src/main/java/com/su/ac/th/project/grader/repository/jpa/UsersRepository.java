package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersModel, Long> {
}
