package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
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

@RestController
@RequestMapping("/rest/admin-ui/typeDocuments")
@RequiredArgsConstructor
@Tag(name = "TypeDocument API")
public class TypeDocumentController {

    private final TypeDocumentService typeDocumentService;

    @GetMapping
    @Operation(summary = "Получить данные о всех типах документов", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public PagedModel<TypeDocumentDto> getAll(@ParameterObject @ModelAttribute TypeDocumentFilter filter, @ParameterObject Pageable pageable) {
        Page<TypeDocumentDto> typeDocumentsDto = typeDocumentService.getAll(filter, pageable);
        return new PagedModel<>(typeDocumentsDto);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Получить данные о конкретном типе документа", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public TypeDocumentDto getOne(@PathVariable String name) {
        return typeDocumentService.getOne(name);
    }

    @PostMapping
    @Operation(summary = "создать тип документа", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public TypeDocumentDto create(@RequestParam String name) {
        return typeDocumentService.create(name);
    }

    @PatchMapping("/{name}")
    @Operation(summary = "изменить тип документа", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public TypeDocumentDto patch(@PathVariable String name, @RequestParam String changeName) throws IOException {
        return typeDocumentService.patch(name, changeName);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "удалить тип документа", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public TypeDocumentDto delete(@PathVariable String name) {
        return typeDocumentService.delete(name);
    }

}
