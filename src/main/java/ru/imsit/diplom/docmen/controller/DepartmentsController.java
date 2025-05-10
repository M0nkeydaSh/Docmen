package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Departments", description = "API для работы с департаментами")
public class DepartmentsController {

    private final DepartmentsService departmentsService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех департаментах", description = "В ответе возвращается объекты DepartmentsDto")
    public PagedModel<DepartmentsDto> getAll(@ParameterObject @ModelAttribute DepartmentsFilter filter, @ParameterObject Pageable pageable) {
        Page<DepartmentsDto> departmentsDto = departmentsService.getAll(filter, pageable);
        return new PagedModel<>(departmentsDto);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном департаменте", description = "В ответе возвращается объект DepartmentsDto")
    public DepartmentsDto getOne(@Schema(description = "ID департамента") @RequestParam UUID id) {
        return departmentsService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать департамент", description = "В ответе возвращается объект DepartmentsDto")
    public DepartmentsDto create(@Schema(description = "Название департамента") @RequestParam String name) {
        return departmentsService.create(name);
    }

    @PatchMapping
    @Operation(summary = "Изменить департамент", description = "В ответе возвращается объект DepartmentsDto")
    public DepartmentsDto patch(@Schema(description = "ID департамента") @RequestParam UUID id,
                                @Schema(description = "Название департамента")@RequestParam String name) throws IOException {
        return departmentsService.patch(id, name);
    }

    @DeleteMapping
    @Operation(summary = "Удалить департамент", description = "В ответе возвращается объект DepartmentsDto")
    public DepartmentsDto delete(@Schema(description = "ID департамента") @RequestParam UUID id) {
        return departmentsService.delete(id);
    }

}
