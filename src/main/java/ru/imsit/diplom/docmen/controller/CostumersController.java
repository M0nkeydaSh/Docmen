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
import ru.imsit.diplom.docmen.dto.CostumersDto;
import ru.imsit.diplom.docmen.filtr.CostumersFilter;
import ru.imsit.diplom.docmen.service.CostumersService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/costumers")
@RequiredArgsConstructor
@Tag(name = "Costumers API")
public class CostumersController {

    private final CostumersService costumersService;

    @GetMapping
    @Operation(summary = "Получить данные о всех costumer", description = "В ответе возвращается объект CostumerDto c полями id, firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public PagedModel<CostumersDto> getAll(@ParameterObject @ModelAttribute CostumersFilter filter, @ParameterObject Pageable pageable) {
        Page<CostumersDto> costumersDtos = costumersService.getAll(filter, pageable);
        return new PagedModel<>(costumersDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные о конкретном costumer", description = "В ответе возвращается объект CostumerDto c полями id, firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public CostumersDto getOne(@PathVariable UUID id) {
        return costumersService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать costumer", description = "В ответе возвращается объект CostumerDto c полями id, firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public CostumersDto create(@RequestBody CostumersDto dto) {
        return costumersService.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменить costumer", description = "В ответе возвращается объект CostumerDto c полями id, firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public CostumersDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return costumersService.patch(id, patchNode);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить costumer", description = "В ответе возвращается объект CostumerDto c полями id, firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public CostumersDto delete(@PathVariable UUID id) {
        return costumersService.delete(id);
    }


}
