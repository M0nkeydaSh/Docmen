package ru.imsit.diplom.docmen.dto;

import lombok.Value;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.Costumers}
 */
@Value
public class CostumersDto {
    String firstName;
    String surName;
    String lastName;
    String email;
    String gender;
    String phoneNumber;
    TypeCostumerDto typeCostumer;
    String userName;
}