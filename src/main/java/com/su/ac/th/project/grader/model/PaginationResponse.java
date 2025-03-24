package com.su.ac.th.project.grader.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {
    private List<T> data;
    private Integer totalPages;
    private Long totalRecords;
    private Integer page;
    private Integer size;
}
