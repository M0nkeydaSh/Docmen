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
import ru.imsit.diplom.docmen.dto.CostumersDto;
import ru.imsit.diplom.docmen.enums.GenderEnum;
import ru.imsit.diplom.docmen.filter.CostumersFilter;
import ru.imsit.diplom.docmen.service.CostumersService;

import java.io.IOException;

@RestController
@RequestMapping("/rest/admin-ui/costumers")
@RequiredArgsConstructor
@Tag(name = "Costumers API")
public class CostumersController {

    private final CostumersService costumersService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех costumer", description = "В ответе возвращается объекты CostumerDto")
    public PagedModel<CostumersDto> getAll(@ParameterObject @ModelAttribute CostumersFilter filter, @ParameterObject Pageable pageable) {
        Page<CostumersDto> costumersDto = costumersService.getAll(filter, pageable);
        return new PagedModel<>(costumersDto);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном costumer", description = "В ответе возвращается объект CostumerDto")
    public CostumersDto getOne(@Schema(description = "Логин сотрудника") @RequestParam String username) {
        return costumersService.getOne(username);
    }

    @PostMapping
    @Operation(summary = "Создать costumer", description = "В ответе возвращается объект CostumerDto")
    public CostumersDto create(@Schema(description = "Имя сотрудника") @RequestParam String firstname,
                               @Schema(description = "Отчество сотрудника") @RequestParam(required = false) String surName,
                               @Schema(description = "Фамилия сотрудника") @RequestParam String lastName,
                               @Schema(description = "Почта сотрудника") @RequestParam String email,
                               @Schema(description = "Телефон сотрудника") @RequestParam String phoneNumber,
                               @Schema(description = "Пол сотрудника", implementation = GenderEnum.class, requiredMode = Schema.RequiredMode.REQUIRED) @RequestParam String gender,
                               @Schema(description = "Должность сотрудника") @RequestParam String typeCostumer,
                               @Schema(description = "Логин сотрудника") @RequestParam String username) {
        return costumersService.create(firstname, surName, lastName, email, phoneNumber, gender, typeCostumer, username);
    }

    @PatchMapping
    @Operation(summary = "Изменить costumer", description = "В ответе возвращается объект CostumerDto")
    public CostumersDto patch(@Schema(description = "Логин сотрудника") @RequestParam String username,
                              @Schema(description = "Имя сотрудника") @RequestParam String firstname,
                              @Schema(description = "Фамилия сотрудника") @RequestParam String surName,
                              @Schema(description = "Логин сотрудника") @RequestParam String lastName,
                              @Schema(description = "Почта сотрудника") @RequestParam String email,
                              @Schema(description = "Телефон сотрудника") @RequestParam String phoneNumber,
                              @Schema(description = "Пол сотрудника", implementation = GenderEnum.class, requiredMode = Schema.RequiredMode.REQUIRED) @RequestParam String gender,
                              @Schema(description = "Должность сотрудника") @RequestParam String typeCostumer) throws IOException {
        return costumersService.patch(username, firstname, surName, lastName, email, phoneNumber, gender, typeCostumer);
    }

    @DeleteMapping
    @Operation(summary = "Удалить costumer", description = "В ответе возвращается объект CostumerDto")
    public CostumersDto delete(@Schema(description = "Логин сотрудника") @RequestParam String username) {
        return costumersService.delete(username);
    }


}
