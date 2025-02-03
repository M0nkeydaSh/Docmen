package ru.imsit.diplom.docmen.dto;

import lombok.Value;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.Comments}
 */
@Value
public class CommentsDto {
    String id;
    String content;
}