package ru.imsit.diplom.docmen.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.imsit.diplom.docmen.dto.TypeDocumentDto;
import ru.imsit.diplom.docmen.entity.TypeDocument;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TypeDocumentMapper {
    TypeDocument toEntity(TypeDocumentDto typeDocumentDto);

    TypeDocumentDto toTypeDocumentDto(TypeDocument typeDocument);
}