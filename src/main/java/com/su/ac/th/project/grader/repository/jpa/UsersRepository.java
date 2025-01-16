package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.model.UsersModel;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<UsersModel, Long> {
}
