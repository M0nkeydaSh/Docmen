package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.*;
import ru.imsit.diplom.docmen.dto.HistoryDto;
import ru.imsit.diplom.docmen.entity.History;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {DocCardMapper.class, UserMapper.class})
public interface HistoryMapper {
    History toEntity(HistoryDto historyDto);

    @Mapping(target = "userName", source = "history.user.username")
    HistoryDto toHistoryDto(History history);

    History updateWithNull(HistoryDto historyDto, @MappingTarget History history);
}