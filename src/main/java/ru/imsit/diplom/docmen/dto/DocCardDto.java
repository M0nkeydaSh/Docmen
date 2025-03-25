package ru.imsit.diplom.docmen.dto;

import lombok.Value;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.DocCard}
 */
@Value
public class DocCardDto {
    String name;
    String description;
    String userName;
    TypeDocumentDto typeDocument;
    String regNum;
    String keyWords;
}