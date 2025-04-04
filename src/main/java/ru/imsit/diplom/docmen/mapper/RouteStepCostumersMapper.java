package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.RouteStepCostumersDto;
import ru.imsit.diplom.docmen.entity.RouteStepCostumers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {RouteStepMapper.class, CostumersMapper.class})
public interface RouteStepCostumersMapper {
    RouteStepCostumers toEntity(RouteStepCostumersDto routeStepCostumersDto);

    RouteStepCostumersDto toRouteStepParticipantsDto(RouteStepCostumers routeStepCostumers);

    RouteStepCostumers updateWithNull(RouteStepCostumersDto routeStepCostumersDto, @MappingTarget RouteStepCostumers routeStepCostumers);
}