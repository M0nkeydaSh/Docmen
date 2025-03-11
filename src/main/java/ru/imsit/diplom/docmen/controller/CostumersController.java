package ru.imsit.diplom.docmen.controller;

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

@RestController
@RequestMapping("/rest/admin-ui/costumers")
@RequiredArgsConstructor
@Tag(name = "Costumers API")
public class CostumersController {

    private final CostumersService costumersService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех costumer", description = "В ответе возвращается объект CostumerDto c полями id, firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public PagedModel<CostumersDto> getAll(@ParameterObject @ModelAttribute CostumersFilter filter, @ParameterObject Pageable pageable) {
        Page<CostumersDto> costumersDtos = costumersService.getAll(filter, pageable);
        return new PagedModel<>(costumersDtos);
    }

    @GetMapping
    @Operation(summary = "Получить данные о конкретном costumer", description = "В ответе возвращается объект CostumerDto c полями id, firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public CostumersDto getOne(@RequestParam String username) {
        return costumersService.getOne(username);
    }

    @PostMapping
    @Operation(summary = "Создать costumer", description = "В ответе возвращается объект CostumerDto c полями id, firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public CostumersDto create(@RequestParam String firstname, @RequestParam String surName, @RequestParam String lastName,
                               @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String gender,
                               @RequestParam String typeCostumer, @RequestParam String username) {
        return costumersService.create(firstname, surName, lastName, email, phoneNumber, gender, typeCostumer, username);
    }

    @PatchMapping
    @Operation(summary = "Изменить costumer", description = "В ответе возвращается объект CostumerDto c полями firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public CostumersDto patch(@RequestParam String username, @RequestParam String firstname, @RequestParam String surName, @RequestParam String lastName,
                              @RequestParam String email, @RequestParam String phoneNumber, @RequestParam String gender,
                              @RequestParam String typeCostumer) throws IOException {
        return costumersService.patch(username, firstname, surName, lastName, email, phoneNumber, gender, typeCostumer);
    }

    @DeleteMapping
    @Operation(summary = "Удалить costumer", description = "В ответе возвращается объект CostumerDto c полями id, firstName, surName, lastName, email, gender, phoneNumber, typeCostumer, user.")
    public CostumersDto delete(@RequestParam String username) {
        return costumersService.delete(username);
    }


}
