package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.*;
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.entity.DocCard;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserMapper.class})
public interface DocCardMapper {
    DocCard toEntity(DocCardDto docCardDto);

    @Mapping(target = "userName", source = "docCard.user.username")
    DocCardDto toDocCardDto(DocCard docCard);

    DocCard updateWithNull(DocCardDto docCardDto, @MappingTarget DocCard docCard);
}