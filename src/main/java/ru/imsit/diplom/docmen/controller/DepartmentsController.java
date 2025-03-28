package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.DepartmentsDto;
import ru.imsit.diplom.docmen.filter.DepartmentsFilter;
import ru.imsit.diplom.docmen.service.DepartmentsService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/departments")
@RequiredArgsConstructor
@Tag(name = "Departments API")
public class DepartmentsController {

    private final DepartmentsService departmentsService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех департаментах", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public PagedModel<DepartmentsDto> getAll(@ParameterObject @ModelAttribute DepartmentsFilter filter, @ParameterObject Pageable pageable) {
        Page<DepartmentsDto> departmentsDto = departmentsService.getAll(filter, pageable);
        return new PagedModel<>(departmentsDto);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном департаменте", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public DepartmentsDto getOne(@RequestParam UUID id) {
        return departmentsService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать департамент", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public DepartmentsDto create(@RequestParam String name) {
        return departmentsService.create(name);
    }

    @PatchMapping
    @Operation(summary = "Изменить департамент", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public DepartmentsDto patch(@RequestParam UUID id, @RequestParam String name) throws IOException {
        return departmentsService.patch(id, name);
    }

    @DeleteMapping
    @Operation(summary = "Удалить департамент", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public DepartmentsDto delete(@RequestParam UUID id) {
        return departmentsService.delete(id);
    }

}
