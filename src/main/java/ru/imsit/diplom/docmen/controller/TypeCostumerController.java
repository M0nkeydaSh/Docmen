package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.TypeCostumerDto;
import ru.imsit.diplom.docmen.filtr.TypeCostumerFilter;
import ru.imsit.diplom.docmen.service.TypeCostumerService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/typeCostumers")
@RequiredArgsConstructor
public class TypeCostumerController {

    private final TypeCostumerService typeCostumerService;

    @GetMapping
    public PagedModel<TypeCostumerDto> getAll(@ParameterObject @ModelAttribute TypeCostumerFilter filter, @ParameterObject Pageable pageable) {
        Page<TypeCostumerDto> typeCostumerDtos = typeCostumerService.getAll(filter, pageable);
        return new PagedModel<>(typeCostumerDtos);
    }

    @GetMapping("/{id}")
    public TypeCostumerDto getOne(@PathVariable UUID id) {
        return typeCostumerService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<TypeCostumerDto> getMany(@RequestParam List<UUID> ids) {
        return typeCostumerService.getMany(ids);
    }

    @PostMapping
    public TypeCostumerDto create(@RequestBody TypeCostumerDto dto) {
        return typeCostumerService.create(dto);
    }

    @PatchMapping("/{id}")
    public TypeCostumerDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return typeCostumerService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return typeCostumerService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public TypeCostumerDto delete(@PathVariable UUID id) {
        return typeCostumerService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        typeCostumerService.deleteMany(ids);
    }
}
