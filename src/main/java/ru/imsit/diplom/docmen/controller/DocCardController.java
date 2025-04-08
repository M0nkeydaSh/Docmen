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
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.enums.StatesEnum;
import ru.imsit.diplom.docmen.filter.DocCardFilter;
import ru.imsit.diplom.docmen.service.DocCardService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/docCards")
@RequiredArgsConstructor
@Tag(name = "DocCard API")
public class DocCardController {

    private final DocCardService docCardService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех карточках", description = "В ответе возвращается объекты DocCardDto")
    public PagedModel<DocCardDto> getAll(@ParameterObject @ModelAttribute DocCardFilter filter, @ParameterObject Pageable pageable) {
        Page<DocCardDto> docCardDtos = docCardService.getAll(filter, pageable);
        return new PagedModel<>(docCardDtos);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретной карточке", description = "В ответе возвращается объект DocCardDto")
    public DocCardDto getOne(@Schema(description = "ID карточки документа") @RequestParam UUID id) {
        return docCardService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать карточку", description = "В ответе возвращается объект DocCardDto")
    public DocCardDto create(@Schema(description = "Имя карточки документа") @RequestParam String name,
                             @Schema(description = "Описание карточки документа") @RequestParam String description,
                             @Schema(description = "Статус карточки документа", implementation = StatesEnum.class, requiredMode = Schema.RequiredMode.REQUIRED) @RequestParam String state,
                             @Schema(description = "Тип карточки документа") @RequestParam String typeDocument,
                             @Schema(description = "Регистрационный номер карточки документа") @RequestParam String regNum,
                             @Schema(description = "Ключевые слова карточки документа") @RequestParam String keyWords) {
        return docCardService.create(name, description, typeDocument, regNum, keyWords, state);
    }

    @PatchMapping
    @Operation(summary = "Изменить карточку", description = "В ответе возвращается объект DocCardDto")
    public DocCardDto patch(@Schema(description = "ID карточки документа") @RequestParam UUID id,
                            @Schema(description = "Имя карточки документа") @RequestParam String name,
                            @Schema(description = "Описание карточки документа") @RequestParam String description,
                            @Schema(description = "Статус карточки документа") @RequestParam String state,
                            @Schema(description = "Тип карточки документа") @RequestParam String typeDocument,
                            @Schema(description = "Регистрационный номер карточки документа") @RequestParam String regNum,
                            @Schema(description = "Ключевые слова карточки документа")  @RequestParam String keyWords) throws IOException {
        return docCardService.patch(id, name, description, typeDocument, regNum, keyWords, state);
    }

    @DeleteMapping
    @Operation(summary = "Удалить карточку", description = "В ответе возвращается объект DocCardDto")
    public DocCardDto delete(@Schema(description = "ID карточки документа") @RequestParam UUID id) {
        return docCardService.delete(id);
    }

}
