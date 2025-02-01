package ru.imsit.diplom.docmen.dto;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.Files}
 */
@Value
public class FilesDto {
    UUID id;
    String name;
    String changeDate;
}