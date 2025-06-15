package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.imsit.diplom.docmen.dto.FilesDto;
import ru.imsit.diplom.docmen.filter.FilesFilter;
import ru.imsit.diplom.docmen.service.FilesService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/files")
@RequiredArgsConstructor
@Tag(name = "Files", description = "API для работы с файлами")
public class FilesController {

    private final FilesService filesService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех файлах", description = "В ответе возвращается объекты FilesDto")
    public PagedModel<FilesDto> getAll(@ParameterObject @ModelAttribute FilesFilter filter, @ParameterObject Pageable pageable) {
        Page<FilesDto> filesDto = filesService.getAll(filter, pageable);
        return new PagedModel<>(filesDto);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном файле", description = "В ответе возвращается объект FilesDto")
    public FilesDto getOne(@Schema(description = "ID файла") @RequestParam UUID id) {
        return filesService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать файл", description = "В ответе возвращается объект FilesDto")
    public FilesDto create(@Schema(description = "Имя файла") @RequestParam String name,
                           @Schema(description = "ID карточки документа файла") @RequestParam String docCardId) {
        return filesService.create(name, docCardId);
    }

    @PatchMapping
    @Operation(summary = "Изменить файл", description = "В ответе возвращается объект FilesDto")
    public FilesDto patch(@Schema(description = "ID файла") @RequestParam UUID id,
                          @Schema(description = "Новое имя файла") @RequestParam String name) throws IOException {
        return filesService.patch(id, name);
    }

    @DeleteMapping
    @Operation(summary = "Удалить файл", description = "В ответе возвращается объект FilesDto")
    public FilesDto delete(@Schema(description = "ID файла") @RequestParam UUID id) {
        return filesService.delete(id);
    }


    @PostMapping("/upload")
    @Operation(summary = "Загрузить файл")
    public ResponseEntity<?> upload(@Schema(description = "ID карточки документа файла") @RequestParam String docCardId,
                                    @RequestParam("file") MultipartFile file) {
        try {
            filesService.upload(docCardId, file);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.ok("Загружен файл: '" + file.getOriginalFilename() + "' в карточку документа с ID=" + docCardId);
    }

    @GetMapping("/download/")
    @Operation(summary = "Скачать файл")
    public ResponseEntity<?> download(@Schema(description = "ID карточки документа файла") @RequestParam String docCardId,
                                      @Schema(description = "Имя файла") @RequestParam String fileName) throws FileNotFoundException {
        return filesService.downloadFile(fileName, docCardId);
    }
}
