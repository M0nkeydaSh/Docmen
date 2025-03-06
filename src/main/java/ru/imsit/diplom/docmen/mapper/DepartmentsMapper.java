package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.DepartmentsDto;
import ru.imsit.diplom.docmen.entity.Departments;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentsMapper {
    Departments toEntity(DepartmentsDto departmentsDto);

    DepartmentsDto toDepartmentsDto(Departments departments);

    Departments updateWithNull(DepartmentsDto departmentsDto, @MappingTarget Departments departments);
}