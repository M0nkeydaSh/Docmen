package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
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
public class UserController {

    private final UserService userService;

    @GetMapping
    public PagedModel<UserDto> getAll(@ModelAttribute UserFilter filter, Pageable pageable) {
        Page<UserDto> userDtos = userService.getAll(filter, pageable);
        return new PagedModel<>(userDtos);
    }

    @GetMapping("/{id}")
    public UserDto getOne(@PathVariable UUID id) {
        return userService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<UserDto> getMany(@RequestParam List<UUID> ids) {
        return userService.getMany(ids);
    }

    @PostMapping
    public UserDto create(@RequestBody UserDto dto) {
        return userService.create(dto);
    }

    @PatchMapping("/{id}")
    public UserDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return userService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return userService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public UserDto delete(@PathVariable UUID id) {
        return userService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        userService.deleteMany(ids);
    }
}
