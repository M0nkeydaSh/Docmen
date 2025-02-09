package ru.imsit.diplom.docmen.dto;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.TypeCostumer}
 */
@Value
public class TypeCostumerDto {
    UUID id;
    String name;
    DepartmentsDto departments;
}