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
import ru.imsit.diplom.docmen.filtr.DepartmentsFilter;
import ru.imsit.diplom.docmen.service.DepartmentsService;

import java.io.IOException;

@RestController
@RequestMapping("/rest/admin-ui/departments")
@RequiredArgsConstructor
@Tag(name = "Departments API")
public class DepartmentsController {

    private final DepartmentsService departmentsService;

    @GetMapping
    @Operation(summary = "Получить данные о всех департаментах", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public PagedModel<DepartmentsDto> getAll(@ParameterObject @ModelAttribute DepartmentsFilter filter, @ParameterObject Pageable pageable) {
        Page<DepartmentsDto> departmentsDtos = departmentsService.getAll(filter, pageable);
        return new PagedModel<>(departmentsDtos);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Получить данные о конкретном департаменте", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public DepartmentsDto getOne(@PathVariable String name) {
        return departmentsService.getOne(name);
    }

    @PostMapping
    @Operation(summary = "Создать департамент", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public DepartmentsDto create(@RequestParam String name) {
        return departmentsService.create(name);
    }

    @PatchMapping("/{name}")
    @Operation(summary = "Изменить департамент", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public DepartmentsDto patch(@PathVariable String name, @RequestParam String changeName) throws IOException {
        return departmentsService.patch(name, changeName);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить департамент", description = "В ответе возвращается объект DepartmentsDto c полем name.")
    public DepartmentsDto delete(@PathVariable String name) {
        return departmentsService.delete(name);
    }

}
