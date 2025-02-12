package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.entity.DocCard;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface DocCardMapper {
    DocCard toEntity(DocCardDto docCardDto);

    DocCardDto toDocCardDto(DocCard docCard);

    DocCard updateWithNull(DocCardDto docCardDto, @MappingTarget DocCard docCard);
}