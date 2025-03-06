package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.*;
import ru.imsit.diplom.docmen.dto.FilesDto;
import ru.imsit.diplom.docmen.entity.Files;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilesMapper {
    Files toEntity(FilesDto filesDto);

    @Mapping(target = "userName", source = "files.user.username")
    FilesDto toFilesDto(Files files);

    Files updateWithNull(FilesDto filesDto, @MappingTarget Files files);
}