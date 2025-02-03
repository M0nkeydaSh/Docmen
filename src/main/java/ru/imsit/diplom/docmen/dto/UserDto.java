package ru.imsit.diplom.docmen.dto;

import lombok.Value;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.User}
 */
@Value
public class UserDto {
    String id;
    String login;
    String password;
}