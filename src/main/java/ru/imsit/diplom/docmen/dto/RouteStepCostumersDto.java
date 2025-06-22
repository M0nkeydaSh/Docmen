package ru.imsit.diplom.docmen.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Value;
import ru.imsit.diplom.docmen.entity.RouteStepCostumers;


/**
 * DTO for {@link RouteStepCostumers}
 */
@Value
public class RouteStepCostumersDto {
    String id;
    String routeStepId;
    String costumerId;
    @JsonIgnore
    String ready;
    String controlDate;
}