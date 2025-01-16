package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.model.request.UsersRequest;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UsersRequest, Long> {
}
