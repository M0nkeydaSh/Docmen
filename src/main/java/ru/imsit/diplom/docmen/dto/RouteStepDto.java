package ru.imsit.diplom.docmen.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;
import ru.imsit.diplom.docmen.enums.RouteStepStatesEnum;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.RouteStep}
 */
@Value
public class RouteStepDto {
    String id;
    String docCardId;
    String number;
    @ArraySchema(schema = @Schema(description = "Статус шага маршрута", example = "Согласование", implementation = RouteStepStatesEnum.class, requiredMode = Schema.RequiredMode.REQUIRED))
    String routeStepState;

}