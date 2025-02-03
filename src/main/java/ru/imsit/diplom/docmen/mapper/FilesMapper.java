package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.FilesDto;
import ru.imsit.diplom.docmen.entity.Files;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FilesMapper {
    Files toEntity(FilesDto filesDto);

    FilesDto toFilesDto(Files files);

    Files updateWithNull(FilesDto filesDto, @MappingTarget Files files);
}