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
import ru.imsit.diplom.docmen.dto.TypeDocumentDto;
import ru.imsit.diplom.docmen.filter.TypeDocumentFilter;
import ru.imsit.diplom.docmen.service.TypeDocumentService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/typeDocuments")
@RequiredArgsConstructor
@Tag(name = "TypeDocument", description = "API для работы с типами документов")
public class TypeDocumentController {

    private final TypeDocumentService typeDocumentService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех типах документов", description = "В ответе возвращается объект TypeDocumentDto")
    public PagedModel<TypeDocumentDto> getAll(@ParameterObject @ModelAttribute TypeDocumentFilter filter, @ParameterObject Pageable pageable) {
        Page<TypeDocumentDto> typeDocumentsDto = typeDocumentService.getAll(filter, pageable);
        return new PagedModel<>(typeDocumentsDto);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном типе документа", description = "В ответе возвращается объект TypeDocumentDto")
    public TypeDocumentDto getOne(@Schema(description = "ID типа документа") @RequestParam UUID id) {
        return typeDocumentService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "создать тип документа", description = "В ответе возвращается объект TypeDocumentDto")
    public TypeDocumentDto create(@Schema(description = "Название типа документа") @RequestParam String name) {
        return typeDocumentService.create(name);
    }

    @PatchMapping
    @Operation(summary = "изменить тип документа", description = "В ответе возвращается объект TypeDocumentDto")
    public TypeDocumentDto patch(@Schema(description = "ID типа документа") @RequestParam UUID id,
                                 @Schema(description = "Название типа документа") @RequestParam String name) throws IOException {
        return typeDocumentService.patch(id, name);
    }

    @DeleteMapping
    @Operation(summary = "удалить тип документа", description = "В ответе возвращается объект TypeDocumentDto")
    public TypeDocumentDto delete(@Schema(description = "ID типа документа") @RequestParam UUID id) {
        return typeDocumentService.delete(id);
    }

}
