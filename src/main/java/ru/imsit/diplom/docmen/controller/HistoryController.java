package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.HistoryDto;
import ru.imsit.diplom.docmen.service.HistoryService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/histories")
@RequiredArgsConstructor
@Tag(name = "History API")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    @Operation(summary = "Получить данные о всех историях", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public PagedModel<HistoryDto> getAll(@ParameterObject Pageable pageable) {
        Page<HistoryDto> historyDtos = historyService.getAll(pageable);
        return new PagedModel<>(historyDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные о конкретной истории", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public HistoryDto getOne(@PathVariable UUID id) {
        return historyService.getOne(id);
    }

    @GetMapping("/by-ids")
    @Operation(summary = "Получить данные о множество историй, указанных в параметре", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public List<HistoryDto> getMany(@RequestParam List<UUID> ids) {
        return historyService.getMany(ids);
    }

    @PostMapping
    @Operation(summary = "Создать историю", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public HistoryDto create(@RequestBody HistoryDto dto) {
        return historyService.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменить историю", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public HistoryDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return historyService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить историю", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user")
    public HistoryDto delete(@PathVariable UUID id) {
        return historyService.delete(id);
    }

    @DeleteMapping
    @Operation(summary = "Удалить множество историй", description = "В ответе возвращается список UUID объектов.")
    public void deleteMany(@RequestParam List<UUID> ids) {
        historyService.deleteMany(ids);
    }
}
