package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.CommentsDto;
import ru.imsit.diplom.docmen.filtr.CommentsFilter;
import ru.imsit.diplom.docmen.service.CommentsService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/comments")
@RequiredArgsConstructor
@Tag(name = "Comments API")
public class CommentsController {

    private final CommentsService commentsService;


    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Получить данные о всех коментариях", description = "В ответе возвращаются объекты Comments c полями id, content и userId.")
    public PagedModel<CommentsDto> getAll(@ParameterObject @ModelAttribute CommentsFilter filter, @ParameterObject Pageable pageable) {
        Page<CommentsDto> commentsDtos = commentsService.getAll(filter, pageable);
        return new PagedModel<>(commentsDtos);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('USER')")
    @Operation(summary = "Получить данные о конкретном коментарии", description = "В ответе возвращается объект CommentsDto c полями id, content и userId.")
    public CommentsDto getOne(@PathVariable UUID id) {
        return commentsService.getOne(id);
    }


    @GetMapping("/by-ids")
    @Operation(summary = "Получить данные о множестве коментариев, указанных в параметре", description = "В ответе возвращается список UUID объектов.")
    public List<CommentsDto> getMany(@RequestParam List<UUID> ids) {
        return commentsService.getMany(ids);
    }


    @PostMapping
    @Operation(summary = "Создать коментарий", description = "В ответе возвращается объект CommentsDto c полями id, content и userId.")
    public CommentsDto create(@RequestBody CommentsDto dto) {
        return commentsService.create(dto);
    }


    @PatchMapping("/{id}")
    @Operation(summary = "Изменить коментарий", description = "В ответе возвращается объект CommentsDto c полями id, content и userId.")
    public CommentsDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return commentsService.patch(id, patchNode);
    }


    @PatchMapping
    @Operation(summary = "Изменить множество коментариев", description = "В ответе возвращается список UUID объектов.")
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return commentsService.patchMany(ids, patchNode);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить коментарий", description = "В ответе возвращается объект CommentsDto c полями id, content и userId.")
    public CommentsDto delete(@PathVariable UUID id) {
        return commentsService.delete(id);
    }


    @DeleteMapping
    @Operation(summary = "Удалить множество коментариев", description = "В ответе возвращается список UUID объектов.")
    public void deleteMany(@RequestParam List<UUID> ids) {
        commentsService.deleteMany(ids);
    }
}
