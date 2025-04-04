package ru.imsit.diplom.docmen.dto;

import lombok.Value;
import ru.imsit.diplom.docmen.enums.RouteStepStatesEnum;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.RouteStep}
 */
@Value
public class RouteStepDto {
    DocCardDto docCard;
    String number;
    RouteStepStatesEnum routeStepState;
}