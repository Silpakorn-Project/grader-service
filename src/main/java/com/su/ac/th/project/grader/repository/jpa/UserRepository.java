package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, Long> , JpaSpecificationExecutor<UsersEntity> {
}
