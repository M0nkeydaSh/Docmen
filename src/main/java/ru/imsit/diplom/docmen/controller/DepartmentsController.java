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
import ru.imsit.diplom.docmen.dto.DepartmentsDto;
import ru.imsit.diplom.docmen.filtr.DepartmentsFilter;
import ru.imsit.diplom.docmen.service.DepartmentsService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/departments")
@RequiredArgsConstructor
@Tag(name = "Departments API")
public class DepartmentsController {

    private final DepartmentsService departmentsService;

    @GetMapping
    @Operation(summary = "Получить данные о всех департаментах", description = "В ответе возвращается объект DepartmentsDto c полями id, name, description.")
    public PagedModel<DepartmentsDto> getAll(@ParameterObject @ModelAttribute DepartmentsFilter filter, @ParameterObject Pageable pageable) {
        Page<DepartmentsDto> departmentsDtos = departmentsService.getAll(filter, pageable);
        return new PagedModel<>(departmentsDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные о конкретном департаменте", description = "В ответе возвращается объект DepartmentsDto c полями id, name, description.")
    public DepartmentsDto getOne(@PathVariable UUID id) {
        return departmentsService.getOne(id);
    }

    @GetMapping("/by-ids")
    @Operation(summary = "Получить данные о множество департаментов, указанных в параметре", description = "В ответе возвращается объект DepartmentsDto c полями id, name, description.")
    public List<DepartmentsDto> getMany(@RequestParam List<UUID> ids) {
        return departmentsService.getMany(ids);
    }

    @PostMapping
    @Operation(summary = "Создать департамент", description = "В ответе возвращается объект DepartmentsDto c полями id, name, description.")
    public DepartmentsDto create(@RequestBody DepartmentsDto dto) {
        return departmentsService.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменить департамент", description = "В ответе возвращается объект DepartmentsDto c полями id, name, description.")
    public DepartmentsDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return departmentsService.patch(id, patchNode);
    }

    @PatchMapping
    @Operation(summary = "Изменить множество департаментов", description = "В ответе возвращается список UUID объектов.")
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return departmentsService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить департамент", description = "В ответе возвращается объект DepartmentsDto c полями id, name, description.")
    public DepartmentsDto delete(@PathVariable UUID id) {
        return departmentsService.delete(id);
    }

    @DeleteMapping
    @Operation(summary = "Удалить множество департаментов", description = "В ответе возвращается список UUID объектов.")
    public void deleteMany(@RequestParam List<UUID> ids) {
        departmentsService.deleteMany(ids);
    }
}
