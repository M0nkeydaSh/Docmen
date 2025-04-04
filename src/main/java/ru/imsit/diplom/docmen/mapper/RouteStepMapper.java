package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.RouteStepDto;
import ru.imsit.diplom.docmen.entity.RouteStep;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {DocCardMapper.class})
public interface RouteStepMapper {
    RouteStep toEntity(RouteStepDto routeStepDto);

    RouteStepDto toRouteStepDto(RouteStep routeStep);

    RouteStep updateWithNull(RouteStepDto routeStepDto, @MappingTarget RouteStep routeStep);
}