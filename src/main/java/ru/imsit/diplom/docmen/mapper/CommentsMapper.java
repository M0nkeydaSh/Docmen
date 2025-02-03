package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.CommentsDto;
import ru.imsit.diplom.docmen.entity.Comments;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentsMapper {
    Comments toEntity(CommentsDto commentsDto);

    CommentsDto toCommentsDto(Comments comments);

    Comments updateWithNull(CommentsDto commentsDto, @MappingTarget Comments comments);
}