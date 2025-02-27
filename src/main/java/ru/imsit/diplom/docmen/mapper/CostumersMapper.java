package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.*;
import ru.imsit.diplom.docmen.dto.CostumersDto;
import ru.imsit.diplom.docmen.entity.Costumers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {TypeCostumerMapper.class, UserMapper.class})
public interface CostumersMapper {
    Costumers toEntity(CostumersDto costumersDto);

    @Mapping(target = "userName", source = "costumers.user.username")
    CostumersDto toCostumersDto(Costumers costumers);

    Costumers updateWithNull(CostumersDto costumersDto, @MappingTarget Costumers costumers);
}