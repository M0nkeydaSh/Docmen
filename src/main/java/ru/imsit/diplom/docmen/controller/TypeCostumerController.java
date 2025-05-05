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
import ru.imsit.diplom.docmen.dto.TypeCostumerDto;
import ru.imsit.diplom.docmen.filter.TypeCostumerFilter;
import ru.imsit.diplom.docmen.service.TypeCostumerService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/typeCostumers")
@RequiredArgsConstructor
@Tag(name = "TypeCostumer API")
public class TypeCostumerController {

    private final TypeCostumerService typeCostumerService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех видах работников", description = "В ответе возвращается объект typeCostumerDto")
    public PagedModel<TypeCostumerDto> getAll(@ParameterObject @ModelAttribute TypeCostumerFilter filter, @ParameterObject Pageable pageable) {
        Page<TypeCostumerDto> typeCostumerDto = typeCostumerService.getAll(filter, pageable);
        return new PagedModel<>(typeCostumerDto);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном виде работника ", description = "В ответе возвращается объект typeCostumerDto")
    public TypeCostumerDto getOne(@Schema(description = "ID типа документа") @RequestParam UUID id) {
        return typeCostumerService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать вид работника ", description = "В ответе возвращается объект typeCostumerDto")
    public TypeCostumerDto create(@Schema(description = "Название типа работника") @RequestParam String name, @RequestParam String departments) {
        return typeCostumerService.create(name, departments);
    }

    @PatchMapping
    @Operation(summary = "Изменить вид работника", description = "В ответе возвращается объект typeCostumerDto")
    public TypeCostumerDto patch(@Schema(description = "ID типа документа") @RequestParam UUID id,
                                 @Schema(description = "Название типа работника") @RequestParam String name) throws IOException {
        return typeCostumerService.patch(id, name);
    }

    @DeleteMapping
    @Operation(summary = "Удалить вид работника", description = "В ответе возвращается объект typeCostumerDto")
    public TypeCostumerDto delete(@Schema(description = "ID типа документа") @RequestParam UUID id) {
        return typeCostumerService.delete(id);
    }

}
