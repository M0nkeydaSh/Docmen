package ru.imsit.diplom.docmen.controller;

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
import java.util.Set;

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

    @PostMapping
    @Operation(summary = "Создать пользователя", description = "В ответе возвращается объект userDto c полем username")
    public UserDto create(@RequestParam String username, @RequestParam String password, @RequestParam Set<String> authority) {
        return userService.create(username, password, authority);
    }

    @PatchMapping("/{username}")
    @Operation(summary = "Изменить пользователя", description = "В ответе возвращается объект userDto c полем username.")
    public UserDto patch(@PathVariable String username, @RequestParam boolean enabled, @RequestParam Set<String> authorities) throws IOException {
        return userService.patch(username, enabled, authorities);
    }

    @PatchMapping("/deactivate/{username}")
    @Operation(summary = "Изменить статус активности пользователя", description = "В ответе возвращается объект userDto c полем username.")
    public UserDto patchDeactivate(@PathVariable String username, @RequestParam boolean enabled) throws IOException {
        return userService.patchDeactivate(username, enabled);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PatchMapping("/change-password/{username}")
    @Operation(summary = "Изменить пароль пользователя", description = "В ответе возвращается объект userDto c полем username.")
    public String patchPassword(@PathVariable String username, @RequestParam String password) throws IOException {
        try {
            userService.patchPassword(username, password);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

}
