package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.filter.DocCardFilter;
import ru.imsit.diplom.docmen.service.DocCardService;

import java.io.IOException;

@RestController
@RequestMapping("/rest/admin-ui/docCards")
@RequiredArgsConstructor
@Tag(name = "DocCard API")
public class DocCardController {

    private final DocCardService docCardService;

    @GetMapping
    @Operation(summary = "Получить данные о всех карточках", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public PagedModel<DocCardDto> getAll(@ParameterObject @ModelAttribute DocCardFilter filter, @ParameterObject Pageable pageable) {
        Page<DocCardDto> docCardDtos = docCardService.getAll(filter, pageable);
        return new PagedModel<>(docCardDtos);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Получить данные о конкретной карточке", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public DocCardDto getOne(@RequestParam String name) {
        return docCardService.getOne(name);
    }

    @PostMapping
    @Operation(summary = "Создать карточку", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public DocCardDto create(@RequestParam String name, @RequestParam String description,
                             @RequestParam String typeDocument, @RequestParam String regNum, @RequestParam String keyWords) {
        return docCardService.create(name, description, typeDocument, regNum, keyWords);
    }

    @PatchMapping("/{name}")
    @Operation(summary = "Изменить карточку", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public DocCardDto patch(@RequestParam String name, @RequestParam String description,
                            @RequestParam String typeDocument, @RequestParam String regNum, @RequestParam String keyWords) throws IOException {
        return docCardService.patch(name, description, typeDocument, regNum, keyWords);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить карточку", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public DocCardDto delete(@RequestParam String name) {
        return docCardService.delete(name);
    }

}
