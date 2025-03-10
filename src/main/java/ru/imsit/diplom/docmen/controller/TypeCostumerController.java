package ru.imsit.diplom.docmen.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@RestController
@RequestMapping("/rest/admin-ui/typeCostumers")
@RequiredArgsConstructor
@Tag(name = "TypeCostumer API")
public class TypeCostumerController {

    private final TypeCostumerService typeCostumerService;

    @GetMapping
    @Operation(summary = "Получить данные о всех видах работников", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public PagedModel<TypeCostumerDto> getAll(@ParameterObject @ModelAttribute TypeCostumerFilter filter, @ParameterObject Pageable pageable) {
        Page<TypeCostumerDto> typeCostumerDtos = typeCostumerService.getAll(filter, pageable);
        return new PagedModel<>(typeCostumerDtos);
    }

    @GetMapping("/{name}")
    @Operation(summary = "Получить данные о конкретном виде работника ", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public TypeCostumerDto getOne(@PathVariable String name) {
        return typeCostumerService.getOne(name);
    }

    @PostMapping
    @Operation(summary = "Создать вид работника ", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public TypeCostumerDto create(@RequestParam String name, @RequestParam String departments) {
        return typeCostumerService.create(name, departments);
    }

    @PatchMapping("/{name}")
    @Operation(summary = "Изменить вид работника", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public TypeCostumerDto patch(@PathVariable String name, @RequestParam String changeName) throws IOException {
        return typeCostumerService.patch(name, changeName);
    }

    @DeleteMapping("/{name}")
    @Operation(summary = "Удалить вид работника", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public TypeCostumerDto delete(@PathVariable String name) {
        return typeCostumerService.delete(name);
    }

}
