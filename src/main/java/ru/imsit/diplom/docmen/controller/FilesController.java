package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.FilesDto;
import ru.imsit.diplom.docmen.filter.FilesFilter;
import ru.imsit.diplom.docmen.service.FilesService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/files")
@RequiredArgsConstructor
@Tag(name = "Files API")
public class FilesController {

    private final FilesService filesService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех файлах", description = "В ответе возвращается объект FilesDto c полями  name,  userId.")
    public PagedModel<FilesDto> getAll(@ParameterObject @ModelAttribute FilesFilter filter, @ParameterObject Pageable pageable) {
        Page<FilesDto> filesDtos = filesService.getAll(filter, pageable);
        return new PagedModel<>(filesDtos);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном файле", description = "В ответе возвращается объект FilesDto c полями  name,  userId.")
    public FilesDto getOne(@RequestParam UUID id) {
        return filesService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать файл", description = "В ответе возвращается объект FilesDto c полями  name,  userId.")
    public FilesDto create(@RequestParam String name, @RequestParam String docCard) {
        return filesService.create(name, docCard);
    }

    @PatchMapping
    @Operation(summary = "Изменить файл", description = "В ответе возвращается объект FilesDto c полями  name,  userId.")
    public FilesDto patch(@RequestParam UUID id, @RequestParam String name) throws IOException {
        return filesService.patch(id, name);
    }

    @DeleteMapping
    @Operation(summary = "Удалить файл", description = "В ответе возвращается объект FilesDto c полями  name,  userId.")
    public FilesDto delete(@RequestParam UUID id) {
        return filesService.delete(id);
    }

}
