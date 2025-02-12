package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.HistoryDto;
import ru.imsit.diplom.docmen.service.HistoryService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/histories")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping
    public PagedModel<HistoryDto> getAll(@ParameterObject Pageable pageable) {
        Page<HistoryDto> historyDtos = historyService.getAll(pageable);
        return new PagedModel<>(historyDtos);
    }

    @GetMapping("/{id}")
    public HistoryDto getOne(@PathVariable UUID id) {
        return historyService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<HistoryDto> getMany(@RequestParam List<UUID> ids) {
        return historyService.getMany(ids);
    }

    @PostMapping
    public HistoryDto create(@RequestBody HistoryDto dto) {
        return historyService.create(dto);
    }

    @PatchMapping("/{id}")
    public HistoryDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return historyService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return historyService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public HistoryDto delete(@PathVariable UUID id) {
        return historyService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        historyService.deleteMany(ids);
    }
}
