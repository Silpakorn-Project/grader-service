package com.su.ac.th.project.grader.util;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

public class DtoEntityMapper {

    @Getter
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        // ตั้งค่า Matching Strategy ให้ Strict (ตรงกับชื่อฟิลด์เป๊ะ)
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static <DTO, Entity> Entity mapToEntity(DTO dto, Class<Entity> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public static <Entity, DTO> DTO mapToDto(Entity entity, Class<DTO> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public static <DTO, Entity> List<Entity> mapListToEntity(List<DTO> dtoList, Class<Entity> entityClass) {
        return dtoList.stream()
                .map(dto -> mapToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }

    public static <Entity, DTO> List<DTO> mapListToDto(List<Entity> entityList, Class<DTO> dtoClass) {
        return entityList.stream()
                .map(entity -> mapToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

}
