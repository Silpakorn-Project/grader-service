package com.su.ac.th.project.grader.util;

import com.su.ac.th.project.grader.model.PaginationResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PaginationUtil {
    public static boolean isPaginationValid(Integer offset, Integer limit) {
        return offset != null && offset > 0 && limit != null && limit > 0;
    }

    public static Pageable createPageable(Integer offset, Integer limit, String sortBy, Sort.Direction sortType) {
        return PageRequest.of(offset - 1, limit, Sort.by(sortType, sortBy));
    }

    public static Pageable createPageable(Integer offset, Integer limit, String sortBy, String sortType) {
        return PageRequest.of(offset - 1, limit, Sort.by(getSortDirection(sortType), sortBy));
    }

    public static Pageable createPageable(Integer offset, Integer limit, Sort sort) {
        return PageRequest.of(offset - 1, limit, sort);
    }

    public static <E, D> PaginationResponse<D> createPaginationResponse(Page<E> entityPage, Class<D> dtoClass) {
        List<D> dtoList = DtoEntityMapper.mapListToDto(entityPage.getContent(), dtoClass);

        PaginationResponse<D> response = new PaginationResponse<>();
        response.setData(dtoList);
        response.setTotalRecords(entityPage.getTotalElements());
        response.setTotalPages(entityPage.getTotalPages());

        return response;
    }

    public static <E, D> PaginationResponse<D> createPaginationResponse(List<E> entityList, Class<D> dtoClass) {
        List<D> dtoList = DtoEntityMapper.mapListToDto(entityList, dtoClass);

        PaginationResponse<D> response = new PaginationResponse<>();
        response.setData(dtoList);
        response.setTotalRecords((long) entityList.size());
        response.setTotalPages(1);

        return response;
    }

    public static <D> PaginationResponse<D> createPaginationResponse(
            List<D> dtoList, long totalRecords, int totalPages
    ) {
        PaginationResponse<D> response = new PaginationResponse<>();
        response.setData(dtoList);
        response.setTotalRecords(totalRecords);
        response.setTotalPages(totalPages);
        return response;
    }


    private static Sort.Direction getSortDirection(String sortType) {
        return "asc".equalsIgnoreCase(sortType) ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}
