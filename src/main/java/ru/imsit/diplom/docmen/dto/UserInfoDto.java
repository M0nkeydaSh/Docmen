package ru.imsit.diplom.docmen.dto;

import lombok.Value;

import java.util.Set;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.User}
 */
@Value
public class UserInfoDto {

    String username;

    boolean status;

    Set<String> roles;

}