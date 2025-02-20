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
import ru.imsit.diplom.docmen.dto.TypeCostumerDto;
import ru.imsit.diplom.docmen.filtr.TypeCostumerFilter;
import ru.imsit.diplom.docmen.service.TypeCostumerService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные о конкретном виде работника ", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public TypeCostumerDto getOne(@PathVariable UUID id) {
        return typeCostumerService.getOne(id);
    }

    @GetMapping("/by-ids")
    @Operation(summary = "Получить данные о множестве видов работников , указанных в параметре", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public List<TypeCostumerDto> getMany(@RequestParam List<UUID> ids) {
        return typeCostumerService.getMany(ids);
    }

    @PostMapping
    @Operation(summary = "Создать вид работника ", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public TypeCostumerDto create(@RequestBody TypeCostumerDto dto) {
        return typeCostumerService.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменить вид работника", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public TypeCostumerDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return typeCostumerService.patch(id, patchNode);
    }

    @PatchMapping
    @Operation(summary = "Изменить множество видов работников", description = "В ответе возвращается список UUID объектов.")
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return typeCostumerService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить вид работника", description = "В ответе возвращается объект typeCostumerDto c полями id, name, departments.")
    public TypeCostumerDto delete(@PathVariable UUID id) {
        return typeCostumerService.delete(id);
    }

    @DeleteMapping
    @Operation(summary = "Удалить множество видов работников", description = "В ответе возвращается список UUID объектов.")
    public void deleteMany(@RequestParam List<UUID> ids) {
        typeCostumerService.deleteMany(ids);
    }
}
