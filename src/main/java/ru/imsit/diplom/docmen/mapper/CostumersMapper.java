package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.CostumersDto;
import ru.imsit.diplom.docmen.entity.Costumers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TypeCostumerMapper.class, UserMapper.class})
public interface CostumersMapper {
    Costumers toEntity(CostumersDto costumersDto);

    CostumersDto toCostumersDto(Costumers costumers);

    Costumers updateWithNull(CostumersDto costumersDto, @MappingTarget Costumers costumers);
}