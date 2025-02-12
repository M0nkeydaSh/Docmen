package ru.imsit.diplom.docmen.dto;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.Costumers}
 */
@Value
public class CostumersDto {
    UUID id;
    String firstName;
    String surName;
    String lastName;
    String email;
    String gender;
    String phoneNumber;
    TypeCostumerDto typeCostumer;
    UserIdDto user;
}