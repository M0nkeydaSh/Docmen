package ru.imsit.diplom.docmen.controller;

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

    @GetMapping
    public PagedModel<RouteStepCostumersDto> getAll(@ParameterObject @ModelAttribute RouteStepCostumersFilter filter, @ParameterObject Pageable pageable) {
        Page<RouteStepCostumersDto> routeStepParticipantsDtos = routeStepCostumersService.getAll(filter, pageable);
        return new PagedModel<>(routeStepParticipantsDtos);
    }

    @GetMapping("/{id}")
    public RouteStepCostumersDto getOne(@RequestParam UUID id) {
        return routeStepCostumersService.getOne(id);
    }


    @PostMapping
    public RouteStepCostumersDto create(@RequestParam String routeStep, @RequestParam String costumers, @RequestParam String ready, @RequestParam String dateTime) {
        return routeStepCostumersService.create(routeStep, costumers, ready, dateTime);
    }

    @PatchMapping("/{id}")
    public RouteStepCostumersDto patch(@RequestParam UUID id, @RequestParam String routeStep, @RequestParam String costumers, @RequestParam String ready, @RequestParam String dateTime) throws IOException {
        return routeStepCostumersService.patch(id, routeStep, costumers, ready, dateTime);
    }


    @DeleteMapping("/{id}")
    public RouteStepCostumersDto delete(@RequestParam UUID id) {
        return routeStepCostumersService.delete(id);
    }

}
