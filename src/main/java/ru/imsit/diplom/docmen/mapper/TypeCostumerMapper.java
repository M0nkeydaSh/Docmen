package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.TypeCostumerDto;
import ru.imsit.diplom.docmen.entity.TypeCostumer;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {DepartmentsMapper.class})
public interface TypeCostumerMapper {
    TypeCostumer toEntity(TypeCostumerDto typeCostumerDto);

    TypeCostumerDto toTypeCostumerDto(TypeCostumer typeCostumer);

    TypeCostumer updateWithNull(TypeCostumerDto typeCostumerDto, @MappingTarget TypeCostumer typeCostumer);
}