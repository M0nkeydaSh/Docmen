package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
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
public class FilesController {

    private final FilesService filesService;

    @GetMapping
    public PagedModel<FilesDto> getAll(@ParameterObject @ModelAttribute FilesFilter filter, @ParameterObject Pageable pageable) {
        Page<FilesDto> filesDtos = filesService.getAll(filter, pageable);
        return new PagedModel<>(filesDtos);
    }

    @GetMapping("/{id}")
    public FilesDto getOne(@PathVariable UUID id) {
        return filesService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<FilesDto> getMany(@RequestParam List<UUID> ids) {
        return filesService.getMany(ids);
    }

    @PostMapping
    public FilesDto create(@RequestBody FilesDto dto) {
        return filesService.create(dto);
    }

    @PatchMapping("/{id}")
    public FilesDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return filesService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return filesService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public FilesDto delete(@PathVariable UUID id) {
        return filesService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        filesService.deleteMany(ids);
    }
}
