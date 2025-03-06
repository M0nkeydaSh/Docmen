package ru.imsit.diplom.docmen.dto;

import lombok.Value;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.DocCard}
 */
@Value
public class DocCardDto {
    String name;
    String discription;
    String userName;
    String typeDocument;
    String regNum;
    String keyWords;
    String changeDate;
}