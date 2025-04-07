package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.RouteStepDto;
import ru.imsit.diplom.docmen.filter.RouteStepFilter;
import ru.imsit.diplom.docmen.service.RouteStepService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/routeSteps")
@RequiredArgsConstructor
@Tag(name = "RouteStep API")
public class RouteStepController {

    private final RouteStepService routeStepService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех шагах маршрута", description = "В ответе возвращаются объекты RouteStepDto")
    public PagedModel<RouteStepDto> getAll(@ParameterObject @ModelAttribute RouteStepFilter filter, @ParameterObject Pageable pageable) {
        Page<RouteStepDto> routeStepDtos = routeStepService.getAll(filter, pageable);
        return new PagedModel<>(routeStepDtos);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном шаге маршрута", description = "В ответе возвращается объект RouteStepDto")
    public RouteStepDto getOne(@RequestParam UUID id) {
        return routeStepService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать шаг маршрута", description = "В ответе возвращается объект RouteStepDto")
    public RouteStepDto create(@RequestParam String docCardId, @RequestParam String numberOfStep, @RequestParam String routeStepState) {
        return routeStepService.create(docCardId, numberOfStep, routeStepState);
    }

    @PatchMapping
    @Operation(summary = "Изменить шаг маршрута", description = "В ответе возвращается объект RouteStepDto")
    public RouteStepDto patch(@RequestParam UUID id, @RequestParam String docCardId, @RequestParam String numberOfStep, @RequestParam String routeStepState) throws IOException {
        return routeStepService.patch(id, docCardId, numberOfStep, routeStepState);
    }

    @DeleteMapping
    @Operation(summary = "Удалить шаг маршрута", description = "В ответе возвращается объект RouteStepDto")
    public RouteStepDto delete(@RequestParam UUID id) {
        return routeStepService.delete(id);
    }

}
