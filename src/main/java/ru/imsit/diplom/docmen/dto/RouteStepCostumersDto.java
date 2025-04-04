package ru.imsit.diplom.docmen.dto;

import lombok.Value;
import ru.imsit.diplom.docmen.entity.RouteStepCostumers;

/**
 * DTO for {@link RouteStepCostumers}
 */
@Value
public class RouteStepCostumersDto {
    RouteStepDto routeStep;
    CostumersDto costumer;
    String ready;
    String dateTime;
}