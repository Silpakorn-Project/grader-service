package com.su.ac.th.project.grader.repository.jpa;

import com.su.ac.th.project.grader.entity.ProblemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemsRepository extends JpaRepository<ProblemsEntity, Long> {

    @Query(value = "SELECT problem_id, title, description, difficulty, type, created_at, updated_at " +
            "FROM problems ORDER BY RAND() LIMIT 1", nativeQuery = true)
    ProblemsEntity getRandomProblems();

}
