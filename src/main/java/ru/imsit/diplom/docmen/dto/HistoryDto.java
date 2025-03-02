package ru.imsit.diplom.docmen.dto;

import lombok.Value;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.History}
 */
@Value
public class HistoryDto {
    DocCardDto docCard;
    String userName;
}