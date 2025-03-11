package ru.imsit.diplom.docmen.dto;

import lombok.Value;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.TypeCostumer}
 */
@Value
public class TypeCostumerDto {
    String name;

    DepartmentsDto departments;
}