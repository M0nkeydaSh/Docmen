package ru.imsit.diplom.docmen.dto;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.Departments}
 */
@Value
public class DepartmentsDto {
    UUID id;
    String name;
}