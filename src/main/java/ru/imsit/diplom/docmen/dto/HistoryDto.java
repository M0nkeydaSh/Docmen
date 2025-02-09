package ru.imsit.diplom.docmen.dto;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.History}
 */
@Value
public class HistoryDto {
    UUID id;
    DocCardDto docCard;
    UserDto user;
}