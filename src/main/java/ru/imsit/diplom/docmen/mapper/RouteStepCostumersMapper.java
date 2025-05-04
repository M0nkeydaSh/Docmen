package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.*;
import ru.imsit.diplom.docmen.dto.RouteStepCostumersDto;
import ru.imsit.diplom.docmen.entity.RouteStepCostumers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RouteStepMapper.class, CostumersMapper.class})
public interface RouteStepCostumersMapper {
    RouteStepCostumers toEntity(RouteStepCostumersDto routeStepCostumersDto);

    @Mapping(target = "routeStepId", source = "routeStep.id")
    @Mapping(target = "costumerId", source = "costumers.id")
    RouteStepCostumersDto toRouteStepCostumersDto(RouteStepCostumers routeStepCostumers);

    RouteStepCostumers updateWithNull(RouteStepCostumersDto routeStepCostumersDto, @MappingTarget RouteStepCostumers routeStepCostumers);
}