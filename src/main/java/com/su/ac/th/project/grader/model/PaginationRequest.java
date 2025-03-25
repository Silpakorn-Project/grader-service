package com.su.ac.th.project.grader.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PaginationRequest {
    private int offset;
    private int limit;
    private String sortBy = "createdAt";
    private Sort.Direction sortType = Sort.Direction.ASC;
}
