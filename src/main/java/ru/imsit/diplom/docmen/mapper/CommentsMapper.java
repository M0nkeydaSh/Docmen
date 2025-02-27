package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.*;
import ru.imsit.diplom.docmen.dto.CommentsDto;
import ru.imsit.diplom.docmen.entity.Comments;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentsMapper {
    Comments toEntity(CommentsDto commentsDto);

    @Mapping(target = "userName", source = "comments.user.username")
    CommentsDto toCommentsDto(Comments comments);

    Comments updateWithNull(CommentsDto commentsDto, @MappingTarget Comments comments);
}