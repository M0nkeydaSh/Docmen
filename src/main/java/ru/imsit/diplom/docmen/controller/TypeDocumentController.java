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
import ru.imsit.diplom.docmen.entity.TypeDocument;
import ru.imsit.diplom.docmen.filter.TypeDocumentFilter;
import ru.imsit.diplom.docmen.service.TypeDocumentService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/typeDocuments")
@RequiredArgsConstructor
@Tag(name = "TypeDocument API")
public class TypeDocumentController {

    private final TypeDocumentService typeDocumentService;

    @GetMapping
    @Operation(summary = "Получить данные о всех типах документов", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public PagedModel<TypeDocument> getAll(@ParameterObject @ModelAttribute TypeDocumentFilter filter, @ParameterObject Pageable pageable) {
        Page<TypeDocument> typeDocuments = typeDocumentService.getAll(filter, pageable);
        return new PagedModel<>(typeDocuments);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные о конкретном типе документа", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public TypeDocument getOne(@PathVariable UUID id) {
        return typeDocumentService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "создать тип документа", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public TypeDocument create(@RequestBody TypeDocument typeDocument) {
        return typeDocumentService.create(typeDocument);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "изменить тип документа", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public TypeDocument patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return typeDocumentService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удалить тип документа", description = "В ответе возвращается объект TypeDocumentDto c полем name.")
    public TypeDocument delete(@PathVariable UUID id) {
        return typeDocumentService.delete(id);
    }

}
