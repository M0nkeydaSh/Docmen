package ru.imsit.diplom.docmen.dto;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.Comments}
 */
@Value
public class CommentsDto {
    UUID id;
    String changeDate;
    String content;
}