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
import ru.imsit.diplom.docmen.dto.CommentsDto;
import ru.imsit.diplom.docmen.filtr.CommentsFilter;
import ru.imsit.diplom.docmen.service.CommentsService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/comments")
@RequiredArgsConstructor
@Tag(name = "Comments API")
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping
    @Operation(summary = "Получить данные о всех коментариях", description = "В ответе возвращаются объекты Comments c полями id, content и userId.")
    public PagedModel<CommentsDto> getAll(@ParameterObject @ModelAttribute CommentsFilter filter, @ParameterObject Pageable pageable) {
        Page<CommentsDto> commentsDtos = commentsService.getAll(filter, pageable);
        return new PagedModel<>(commentsDtos);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить данные о конкретном коментарии", description = "В ответе возвращается объект CommentsDto c полями id, content и userId.")
    public CommentsDto getOne(@PathVariable UUID id) {
        return commentsService.getOne(id);
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить коментарий", description = "В ответе возвращается объект CommentsDto c полями id, content и userId.")
    public CommentsDto delete(@PathVariable UUID id) {
        return commentsService.delete(id);
    }

}
