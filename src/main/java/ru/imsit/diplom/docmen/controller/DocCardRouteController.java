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
import ru.imsit.diplom.docmen.dto.DocCardRouteDto;
import ru.imsit.diplom.docmen.filter.DocCardRouteFilter;
import ru.imsit.diplom.docmen.service.DocCardRouteService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/docCardRoutes")
@RequiredArgsConstructor
@Tag(name = "DocCardRoute API")
public class DocCardRouteController {

    private final DocCardRouteService docCardRouteService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех маршрутах карточки", description = "В ответе возвращается объекты DocCardRouteDto")
    public PagedModel<DocCardRouteDto> getAll(@ParameterObject @ModelAttribute DocCardRouteFilter filter, @ParameterObject Pageable pageable) {
        Page<DocCardRouteDto> docCardRoutes = docCardRouteService.getAll(filter, pageable);
        return new PagedModel<>(docCardRoutes);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные об одном маршруте карточки", description = "В ответе возвращается объекты DocCardRouteDto")
    public DocCardRouteDto getOne(@Schema(description = "ID карточки документа") @RequestParam UUID id) {
        return docCardRouteService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать маршрут карточки", description = "В ответе возвращается объекты DocCardRouteDto")
    public DocCardRouteDto create(@Schema(description = "ID пользователя шага маршрута") @RequestParam UUID routeStepCostumersId,
                                  @Schema(description = "Дата выполения") @RequestParam String dateComplete) {
        return docCardRouteService.create(routeStepCostumersId, dateComplete);
    }

    @PatchMapping
    @Operation(summary = "Изменить маршрут карточки", description = "В ответе возвращается объекты DocCardRouteDto")
    public DocCardRouteDto patch(@Schema(description = "ID карточки документа") @RequestParam UUID id,
                                 @Schema(description = "ID пользователя шага маршрута") @RequestParam UUID routeStepCostumersId,
                                 @Schema(description = "Дата выполения") @RequestParam String dateComplete) throws IOException {
        return docCardRouteService.patch(id, routeStepCostumersId, dateComplete);
    }

    @PostMapping("/setReady")
    @Operation(summary = "Установить готовность маршрута карточки", description = "В ответе возвращается объекты DocCardRouteDto")
    public DocCardRouteDto setReady(@Schema(description = "ID карточки документа") @RequestParam UUID id) throws IOException {
        return docCardRouteService.setReady(id);
    }

    @PostMapping("/setUnready")
    @Operation(summary = "Установить Неготовность маршрута карточки", description = "В ответе возвращается объекты DocCardRouteDto")
    public DocCardRouteDto setUnready(@Schema(description = "ID карточки документа") @RequestParam UUID id) throws IOException {
        return docCardRouteService.setUnready(id);
    }

    @DeleteMapping
    @Operation(summary = "Удалить маршрут карточки", description = "В ответе возвращается объекты DocCardRouteDto")
    public DocCardRouteDto delete(@Schema(description = "ID карточки документа") @RequestParam UUID id) {
        return docCardRouteService.delete(id);
    }


}
