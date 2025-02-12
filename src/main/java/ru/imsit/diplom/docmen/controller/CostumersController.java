package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.CostumersDto;
import ru.imsit.diplom.docmen.filtr.CostumersFilter;
import ru.imsit.diplom.docmen.service.CostumersService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/costumers")
@RequiredArgsConstructor
public class CostumersController {

    private final CostumersService costumersService;

    @GetMapping
    public PagedModel<CostumersDto> getAll(@ParameterObject @ModelAttribute CostumersFilter filter, @ParameterObject Pageable pageable) {
        Page<CostumersDto> costumersDtos = costumersService.getAll(filter, pageable);
        return new PagedModel<>(costumersDtos);
    }

    @GetMapping("/{id}")
    public CostumersDto getOne(@PathVariable UUID id) {
        return costumersService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<CostumersDto> getMany(@RequestParam List<UUID> ids) {
        return costumersService.getMany(ids);
    }

    @PostMapping
    public CostumersDto create(@RequestBody CostumersDto dto) {
        return costumersService.create(dto);
    }

    @PatchMapping("/{id}")
    public CostumersDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return costumersService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return costumersService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public CostumersDto delete(@PathVariable UUID id) {
        return costumersService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        costumersService.deleteMany(ids);
    }
}
