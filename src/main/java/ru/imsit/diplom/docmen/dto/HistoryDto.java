package ru.imsit.diplom.docmen.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import ru.imsit.diplom.docmen.enums.StatesEnum;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.History}
 */
@Value
public class HistoryDto {
    DocCardDto docCard;
    String userName;
    @ArraySchema(schema = @Schema(description = "Статус истории", example = "Черновик", implementation = StatesEnum.class, requiredMode = Schema.RequiredMode.REQUIRED))
    String state;
}