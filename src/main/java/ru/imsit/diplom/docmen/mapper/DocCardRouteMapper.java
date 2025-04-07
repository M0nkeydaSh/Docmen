package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.DocCardRouteDto;
import ru.imsit.diplom.docmen.entity.DocCardRoute;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RouteStepCostumersMapper.class})
public interface DocCardRouteMapper {
    DocCardRoute toEntity(DocCardRouteDto docCardRouteDto);

    DocCardRouteDto toDocCardRouteDto(DocCardRoute docCardRoute);
}