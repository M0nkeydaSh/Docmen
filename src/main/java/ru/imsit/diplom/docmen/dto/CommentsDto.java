package ru.imsit.diplom.docmen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.Comments}
 */
@Data
@AllArgsConstructor
public class CommentsDto {
    String content;
    String username;
    DocCardDto docCard;
}