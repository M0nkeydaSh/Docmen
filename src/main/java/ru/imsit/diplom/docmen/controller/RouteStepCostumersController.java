package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.RouteStepCostumersDto;
import ru.imsit.diplom.docmen.filter.RouteStepCostumersFilter;
import ru.imsit.diplom.docmen.service.RouteStepCostumersService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/RouteStepCostumers")
@RequiredArgsConstructor
@Tag(name = "RouteStepCostumers", description = "API для работы с участниками шагами маршрута")
public class RouteStepCostumersController {

    private final RouteStepCostumersService routeStepCostumersService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех пользователях шагов маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public PagedModel<RouteStepCostumersDto> getAll(@ParameterObject @ModelAttribute RouteStepCostumersFilter filter, @ParameterObject Pageable pageable) {
        Page<RouteStepCostumersDto> routeStepParticipantsDto = routeStepCostumersService.getAll(filter, pageable);
        return new PagedModel<>(routeStepParticipantsDto);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном пользователе шага маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public RouteStepCostumersDto getOne(@Schema(description = "ID пользователя шага маршрута") @RequestParam UUID id) {
        return routeStepCostumersService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать пользователя шага маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public RouteStepCostumersDto create(@Schema(description = "ID шага маршрута") @RequestParam String routeStepId,
                                        @Schema(description = "ID работника") @RequestParam String costumerId,
                                        @Schema(description = "Срок исполнения") @RequestParam String dateTime) {
        return routeStepCostumersService.create(routeStepId, costumerId, dateTime);
    }

    @PatchMapping
    @Operation(summary = "Изменить пользователя шага маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public RouteStepCostumersDto patch(@Schema(description = "ID пользователя шага маршрута") @RequestParam UUID id,
                                       @Schema(description = "ID  шага маршрута") @RequestParam String routeStepId,
                                       @Schema(description = "ID работника") @RequestParam String costumerId,
                                       @Schema(description = "Срок исполнения") @RequestParam String dateTime) throws IOException {
        return routeStepCostumersService.patch(id, routeStepId, costumerId,  dateTime);
    }

    @DeleteMapping
    @Operation(summary = "Удалить пользователя шага маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public RouteStepCostumersDto delete(@Schema(description = "ID пользователя шага маршрута") @RequestParam UUID id) {
        return routeStepCostumersService.delete(id);
    }

}
