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
import ru.imsit.diplom.docmen.dto.HistoryDto;
import ru.imsit.diplom.docmen.enums.StatesEnum;
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
    @Operation(summary = "Получить данные о всех историях", description = "В ответе возвращаются объекты HistoryDto")
    public PagedModel<HistoryDto> getAll(@ParameterObject @ModelAttribute HistoryFilter filter, @ParameterObject Pageable pageable) {
        Page<HistoryDto> historyDtos = historyService.getAll(filter, pageable);
        return new PagedModel<>(historyDtos);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретной истории", description = "В ответе возвращается объект HistoryDto")
    public HistoryDto getOne(@Schema(description = "ID истории") @RequestParam UUID id) {
        return historyService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать историю", description = "В ответе возвращается объект HistoryDto")
    public HistoryDto create(@Schema(description = "ID карточки документа") @RequestParam UUID docCardId,
                             @Schema(description = "Статус истории", implementation = StatesEnum.class, requiredMode = Schema.RequiredMode.REQUIRED) @RequestParam String state) {
        return historyService.create(docCardId, state);
    }

    @PatchMapping
    @Operation(summary = "Изменить историю", description = "В ответе возвращается объект HistoryDto")
    public HistoryDto patch(@Schema(description = "ID истории") @RequestParam UUID id,
                            @Schema(description = "ID карточки документа") @RequestParam String docCard,
                            @Schema(description = "Статус истории", implementation = StatesEnum.class, requiredMode = Schema.RequiredMode.REQUIRED) @RequestParam String state) throws IOException {
        return historyService.patch(id, docCard, state);
    }

    @DeleteMapping
    @Operation(summary = "Удалить историю", description = "В ответе возвращается объект HistoryDto")
    public HistoryDto delete(@Schema(description = "ID истории") @RequestParam UUID id) {
        return historyService.delete(id);
    }

}
