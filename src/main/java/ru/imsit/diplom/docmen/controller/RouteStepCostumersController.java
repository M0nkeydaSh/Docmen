package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/rest/admin-ui/routeStepParticipants")
@RequiredArgsConstructor
@Tag(name = "RouteStepParticipants API")
public class RouteStepCostumersController {

    private final RouteStepCostumersService routeStepCostumersService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех пользователях шагов маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public PagedModel<RouteStepCostumersDto> getAll(@ParameterObject @ModelAttribute RouteStepCostumersFilter filter, @ParameterObject Pageable pageable) {
        Page<RouteStepCostumersDto> routeStepParticipantsDtos = routeStepCostumersService.getAll(filter, pageable);
        return new PagedModel<>(routeStepParticipantsDtos);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном пользователе шага маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public RouteStepCostumersDto getOne(@RequestParam UUID id) {
        return routeStepCostumersService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать пользователя шага маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public RouteStepCostumersDto create(@RequestParam String routeStepId, @RequestParam String costumerId, @RequestParam String ready, @RequestParam String dateTime) {
        return routeStepCostumersService.create(routeStepId, costumerId, ready, dateTime);
    }

    @PatchMapping
    @Operation(summary = "изменить пользователя шага маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public RouteStepCostumersDto patch(@RequestParam UUID id, @RequestParam String routeStepId, @RequestParam String costumerId, @RequestParam String ready, @RequestParam String dateTime) throws IOException {
        return routeStepCostumersService.patch(id, routeStepId, costumerId, ready, dateTime);
    }

    @DeleteMapping
    @Operation(summary = "Удалить пользователя шага маршрута", description = "В ответе возвращаются объект RouteStepCostumersDto")
    public RouteStepCostumersDto delete(@RequestParam UUID id) {
        return routeStepCostumersService.delete(id);
    }

}
