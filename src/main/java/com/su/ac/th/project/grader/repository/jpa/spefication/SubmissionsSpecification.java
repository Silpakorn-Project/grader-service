package com.su.ac.th.project.grader.repository.jpa.spefication;

import com.su.ac.th.project.grader.constant.CommonConstant;
import com.su.ac.th.project.grader.entity.SubmissionsEntity;
import org.springframework.data.jpa.domain.Specification;

public class SubmissionsSpecification {

    public static Specification<SubmissionsEntity> hasUserId(Long userId) {
        return (root, query, criteriaBuilder) ->
                userId == null ? null : criteriaBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<SubmissionsEntity> hasProblemId(Long problemId) {
        return (root, query, criteriaBuilder) ->
                problemId == null ? null : criteriaBuilder.equal(root.get("problemId"), problemId);
    }

    public static Specification<SubmissionsEntity> hasLanguage(CommonConstant.Language language) {
        return (root, query, criteriaBuilder) ->
                language == null ? null : criteriaBuilder.equal(root.get("language"), language);
    }

    public static Specification<SubmissionsEntity> hasStatus(CommonConstant.Status status) {
        return (root, query, criteriaBuilder) ->
                status == null ? null : criteriaBuilder.equal(root.get("status"), status);
    }
}