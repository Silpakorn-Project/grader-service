package com.su.ac.th.project.grader.repository.jpa.spefication;

import com.su.ac.th.project.grader.constant.CommonConstant;
import com.su.ac.th.project.grader.entity.ProblemsEntity;
import org.springframework.data.jpa.domain.Specification;

public class ProblemsSpecification {

    public static Specification<ProblemsEntity> hasTitle(String title) {
        return (root, query, criteriaBuilder) ->
                title == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<ProblemsEntity> hasDescription(String description) {
        return (root, query, criteriaBuilder) ->
                description == null ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + description.toLowerCase() + "%");
    }

    public static Specification<ProblemsEntity> hasDifficulty(CommonConstant.ProblemDifficulty difficulty) {
        return (root, query, criteriaBuilder) ->
                difficulty == null ? null : criteriaBuilder.equal(root.get("difficulty"), difficulty);
    }

    public static Specification<ProblemsEntity> hasType(CommonConstant.ProblemType type) {
        return (root, query, criteriaBuilder) ->
                type == null ? null : criteriaBuilder.equal(root.get("type"), type);
    }
}
