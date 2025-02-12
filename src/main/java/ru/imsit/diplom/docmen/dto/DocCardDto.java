package ru.imsit.diplom.docmen.dto;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.DocCard}
 */
@Value
public class DocCardDto {
    UUID id;
    String name;
    String discription;
    UserIdDto user;
    String typeDocument;
    String regNum;
    String keyWords;
    String changeDate;
}