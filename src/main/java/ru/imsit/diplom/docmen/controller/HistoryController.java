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
import ru.imsit.diplom.docmen.filter.HistoryFilter;
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
    @Operation(summary = "Получить данные о всех коментариях", description = "В ответе возвращаются объекты Comments c полями id, content и userId.")
    public PagedModel<HistoryDto> getAll(@ParameterObject @ModelAttribute HistoryFilter filter, @ParameterObject Pageable pageable) {
        Page<HistoryDto> historyDtos = historyService.getAll(filter, pageable);
        return new PagedModel<>(historyDtos);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретной истории", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public HistoryDto getOne(@RequestParam UUID id) {
        return historyService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать историю", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public HistoryDto create(@RequestParam UUID docCardId, @RequestParam String state) {
        return historyService.create(docCardId, state);
    }

    @PatchMapping
    @Operation(summary = "Изменить историю", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user.")
    public HistoryDto patch(@RequestParam UUID id, @RequestParam String docCard, @RequestParam String state) throws IOException {
        return historyService.patch(id, docCard, state);
    }

    @DeleteMapping
    @Operation(summary = "Удалить историю", description = "В ответе возвращается объект HistoryDto c полями id, docCardId, user")
    public HistoryDto delete(@RequestParam UUID id) {
        return historyService.delete(id);
    }

}
