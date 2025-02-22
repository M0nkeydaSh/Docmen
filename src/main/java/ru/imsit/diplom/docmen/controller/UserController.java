package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.UserDto;
import ru.imsit.diplom.docmen.filtr.UserFilter;
import ru.imsit.diplom.docmen.service.UserService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/users")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ADMIN')")
@Tag(name = "User API")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Получить данные о всех пользователях", description = "В ответе возвращается объект userDto c полями id, username, password.")
    public PagedModel<UserDto> getAll(@ModelAttribute UserFilter filter, Pageable pageable) {
        Page<UserDto> userDtos = userService.getAll(filter, pageable);
        return new PagedModel<>(userDtos);
    }

    @GetMapping("/{username}")
    @Operation(summary = "Получить данные о конкретном пользователе", description = "В ответе возвращается объект userDto c полями id, username, password.")
    public UserDto getOne(@PathVariable String username) {
        return userService.getOne(username);
    }

    @GetMapping("/by-ids")
    @Operation(summary = "Получить данные о множестве пользователей, указанных в параметре", description = "В ответе возвращается объект userDto c полями id, username, password.")
    public List<UserDto> getMany(@RequestParam List<UUID> ids) {
        return userService.getMany(ids);
    }

    @PostMapping
    @Operation(summary = "Создать пользователя", description = "В ответе возвращается объект userDto c полями id, username, password.")
    public UserDto create(@RequestBody UserDto dto) {
        return userService.create(dto);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Изменить пользователя", description = "В ответе возвращается объект userDto c полями id, username, password.")
    public UserDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return userService.patch(id, patchNode);
    }

    @PatchMapping
    @Operation(summary = "Изменить множество пользователей", description = "В ответе возвращается список UUID объектов.")
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return userService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя", description = "В ответе возвращается объект userDto c полями id, username, password.")
    public UserDto delete(@PathVariable UUID id) {
        return userService.delete(id);
    }

    @DeleteMapping
    @Operation(summary = "Удалить множество пользователей", description = "В ответе возвращается список UUID объектов.")
    public void deleteMany(@RequestParam List<UUID> ids) {
        userService.deleteMany(ids);
    }
}
