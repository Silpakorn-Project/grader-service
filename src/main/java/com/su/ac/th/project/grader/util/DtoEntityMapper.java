package com.su.ac.th.project.grader.util;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.stream.Collectors;

public class DtoEntityMapper {

    /**
     * -- GETTER --
     *  Getter สำหรับ ModelMapper (กรณีต้องการปรับแต่ง Mapping ด้วยตัวเอง)
     */
    @Getter
    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        // ตั้งค่า Matching Strategy ให้ Strict (ตรงกับชื่อฟิลด์เป๊ะ)
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    /**
     * Map จาก DTO ไปยัง Entity
     */
    public static <DTO, Entity> Entity mapToEntity(DTO dto, Class<Entity> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    /**
     * Map จาก Entity ไปยัง DTO
     */
    public static <Entity, DTO> DTO mapToDto(Entity entity, Class<DTO> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    /**
     * Map List ของ DTO ไปยัง List ของ Entity
     */
    public static <DTO, Entity> List<Entity> mapListToEntity(List<DTO> dtoList, Class<Entity> entityClass) {
        return dtoList.stream()
                .map(dto -> mapToEntity(dto, entityClass))
                .collect(Collectors.toList());
    }

    /**
     * Map List ของ Entity ไปยัง List ของ DTO
     */
    public static <Entity, DTO> List<DTO> mapListToDto(List<Entity> entityList, Class<DTO> dtoClass) {
        return entityList.stream()
                .map(entity -> mapToDto(entity, dtoClass))
                .collect(Collectors.toList());
    }

}
