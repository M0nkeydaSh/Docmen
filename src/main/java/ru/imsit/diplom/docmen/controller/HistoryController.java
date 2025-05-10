package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.imsit.diplom.docmen.dto.HistoryDto;
import ru.imsit.diplom.docmen.filter.HistoryFilter;
import ru.imsit.diplom.docmen.service.HistoryService;

@RestController
@RequestMapping("/rest/admin-ui/histories")
@RequiredArgsConstructor
@Tag(name = "History", description = "API для работы с историями")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех историях", description = "В ответе возвращаются объекты HistoryDto")
    public PagedModel<HistoryDto> getAll(@ParameterObject @ModelAttribute HistoryFilter filter, @ParameterObject Pageable pageable) {
        Page<HistoryDto> historyDto = historyService.getAll(filter, pageable);
        return new PagedModel<>(historyDto);
    }

//    @GetMapping("/getOne")
//    @Operation(summary = "Получить данные о конкретной истории", description = "В ответе возвращается объект HistoryDto")
//    public HistoryDto getOne(@Schema(description = "ID истории") @RequestParam UUID id) {
//        return historyService.getOne(id);
//    }

//    @PostMapping
//    @Operation(summary = "Создать историю", description = "В ответе возвращается объект HistoryDto")
//    public HistoryDto create(@Schema(description = "ID карточки документа") @RequestParam UUID docCardId,
//                             @Schema(description = "Статус истории", implementation = StatesEnum.class, requiredMode = Schema.RequiredMode.REQUIRED) @RequestParam String state) {
//        return historyService.create(docCardId, state);
//    }


//    @DeleteMapping
//    @Operation(summary = "Удалить историю", description = "В ответе возвращается объект HistoryDto")
//    public HistoryDto delete(@Schema(description = "ID истории") @RequestParam UUID id) {
//        return historyService.delete(id);
//    }

}
