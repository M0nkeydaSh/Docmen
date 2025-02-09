package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.DepartmentsDto;
import ru.imsit.diplom.docmen.filtr.DepartmentsFilter;
import ru.imsit.diplom.docmen.service.DepartmentsService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/departments")
@RequiredArgsConstructor
public class DepartmentsController {

    private final DepartmentsService departmentsService;

    @GetMapping
    public PagedModel<DepartmentsDto> getAll(@ParameterObject @ModelAttribute DepartmentsFilter filter, @ParameterObject Pageable pageable) {
        Page<DepartmentsDto> departmentsDtos = departmentsService.getAll(filter, pageable);
        return new PagedModel<>(departmentsDtos);
    }

    @GetMapping("/{id}")
    public DepartmentsDto getOne(@PathVariable UUID id) {
        return departmentsService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<DepartmentsDto> getMany(@RequestParam List<UUID> ids) {
        return departmentsService.getMany(ids);
    }

    @PostMapping
    public DepartmentsDto create(@RequestBody DepartmentsDto dto) {
        return departmentsService.create(dto);
    }

    @PatchMapping("/{id}")
    public DepartmentsDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return departmentsService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return departmentsService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public DepartmentsDto delete(@PathVariable UUID id) {
        return departmentsService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        departmentsService.deleteMany(ids);
    }
}
