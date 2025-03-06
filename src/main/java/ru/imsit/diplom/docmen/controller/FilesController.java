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
import ru.imsit.diplom.docmen.dto.FilesDto;
import ru.imsit.diplom.docmen.filtr.FilesFilter;
import ru.imsit.diplom.docmen.service.FilesService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/files")
@RequiredArgsConstructor
@Tag(name = "Files API")
public class FilesController {

    private final FilesService filesService;

    @GetMapping
    @Operation(summary = "Получить данные о всех файлах", description = "В ответе возвращается объект FilesDto c полями id, name,  userId.")
    public PagedModel<FilesDto> getAll(@ParameterObject @ModelAttribute FilesFilter filter, @ParameterObject Pageable pageable) {
        Page<FilesDto> filesDtos = filesService.getAll(filter, pageable);
        return new PagedModel<>(filesDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные о конкретном файле", description = "В ответе возвращается объект FilesDto c полями id, name,  userId.")
    public FilesDto getOne(@PathVariable UUID id) {
        return filesService.getOne(id);
    }

    @GetMapping("/by-ids")
    @Operation(summary = "Получить данные о множество файлов, указанных в параметре", description = "В ответе возвращается объект FilesDto c полями id, name,  userId.")
    public List<FilesDto> getMany(@RequestParam List<UUID> ids) {
        return filesService.getMany(ids);
    }

    @PostMapping
    @Operation(summary = "Создать файл", description = "В ответе возвращается объект FilesDto c полями id, name,  userId.")
    public FilesDto create(@RequestBody FilesDto dto) {
        return filesService.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменить файл", description = "В ответе возвращается объект FilesDto c полями id, name,  userId.")
    public FilesDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return filesService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить файл", description = "В ответе возвращается объект FilesDto c полями id, name,  userId.")
    public FilesDto delete(@PathVariable UUID id) {
        return filesService.delete(id);
    }

    @DeleteMapping
    @Operation(summary = "Удалить множество файлов", description = "В ответе возвращается список UUID объектов.")
    public void deleteMany(@RequestParam List<UUID> ids) {
        filesService.deleteMany(ids);
    }
}
