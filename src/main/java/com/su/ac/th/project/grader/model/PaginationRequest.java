package com.su.ac.th.project.grader.model;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PaginationRequest {
    private Integer offset;
    private Integer limit;
    private String sortBy = "createdAt";
    private Sort.Direction sortType = Sort.Direction.ASC;

    public void setSortType(String sortType) {
        this.sortType = Sort.Direction.valueOf(sortType.toUpperCase());
    }
}
