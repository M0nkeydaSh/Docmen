package ru.imsit.diplom.docmen.dto;

import lombok.Value;

/**
 * DTO for {@link ru.imsit.diplom.docmen.entity.DocCardRoute}
 */
@Value
public class DocCardRouteDto {
    RouteStepCostumersDto routeStepCostumers;
    String ready;
    String dateComplete;
    RouteStepDto routeStep;
}