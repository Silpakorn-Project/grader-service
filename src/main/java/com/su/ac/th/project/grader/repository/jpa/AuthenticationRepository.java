package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenticationRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByEmail(String username);
    Optional<UsersEntity> findByUsername(String username);

}
