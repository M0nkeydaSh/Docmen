package ru.imsit.diplom.docmen.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import ru.imsit.diplom.docmen.enums.GenderEnum;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.Costumers}
 */
@Value
public class CostumersDto {
    String firstName;
    String surName;
    String lastName;
    String email;
    @ArraySchema(schema = @Schema(description = "Пол сотрудника", example = "М", implementation = GenderEnum.class, requiredMode = Schema.RequiredMode.REQUIRED))
    String gender;
    String phoneNumber;
    TypeCostumerDto typeCostumer;
    String userName;
}