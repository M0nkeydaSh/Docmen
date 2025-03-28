package ru.imsit.diplom.docmen.controller;

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
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/histories")
@RequiredArgsConstructor
@Tag(name = "History API")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех историях", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public PagedModel<HistoryDto> getAll(@ParameterObject Pageable pageable) {
        Page<HistoryDto> historyDtos = historyService.getAll(pageable);
        return new PagedModel<>(historyDtos);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретной истории", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public HistoryDto getOne(@RequestParam UUID id) {
        return historyService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать историю", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public HistoryDto create(@RequestParam String docCard, @RequestParam String userName, @RequestParam String state) {
        return historyService.create(docCard, userName, state);
    }

    @PatchMapping
    @Operation(summary = "Изменить историю", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public HistoryDto patch(@RequestParam UUID id, @RequestParam String docCard, @RequestParam String userName, @RequestParam String state) throws IOException {
        return historyService.patch(id, docCard, userName, state);
    }

    @DeleteMapping
    @Operation(summary = "Удалить историю", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user")
    public HistoryDto delete(@RequestParam UUID id) {
        return historyService.delete(id);
    }

}
