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
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.filtr.DocCardFilter;
import ru.imsit.diplom.docmen.service.DocCardService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные о конкретной карточке", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public DocCardDto getOne(@PathVariable UUID id) {
        return docCardService.getOne(id);
    }

    @GetMapping("/by-ids")
    @Operation(summary = "Получить данные о множество карточек, указанных в параметре", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public List<DocCardDto> getMany(@RequestParam List<UUID> ids) {
        return docCardService.getMany(ids);
    }

    @PostMapping
    @Operation(summary = "Создать карточку", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public DocCardDto create(@RequestBody DocCardDto dto) {
        return docCardService.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменить карточку", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public DocCardDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return docCardService.patch(id, patchNode);
    }

    @PatchMapping
    @Operation(summary = "Изменить множество карточек", description = "В ответе возвращается список UUID объектов.")
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return docCardService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить карточку", description = "В ответе возвращается объект DocCardDto c полями id, name, description, user, typeDocument, regNum, keyWords, changeDate.")
    public DocCardDto delete(@PathVariable UUID id) {
        return docCardService.delete(id);
    }

    @DeleteMapping
    @Operation(summary = "Удалить множество карточек", description = "В ответе возвращается список UUID объектов.")
    public void deleteMany(@RequestParam List<UUID> ids) {
        docCardService.deleteMany(ids);
    }
}
