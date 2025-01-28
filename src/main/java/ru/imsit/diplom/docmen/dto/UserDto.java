package ru.imsit.diplom.docmen.dto;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.User}
 */
@Value
public class UserDto {
    UUID id;
    String login;
    String password;
}