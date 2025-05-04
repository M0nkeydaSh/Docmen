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
import ru.imsit.diplom.docmen.dto.CommentsDto;
import ru.imsit.diplom.docmen.filter.CommentsFilter;
import ru.imsit.diplom.docmen.service.CommentsService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/comments")
@RequiredArgsConstructor
@Tag(name = "Comments API")
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping("/getAll")
    @Operation(summary = "Получить данные о всех комментариях", description = "В ответе возвращаются объекты CommentsDto.")
    public PagedModel<CommentsDto> getAll(@ParameterObject @ModelAttribute CommentsFilter filter, @ParameterObject Pageable pageable) {
        Page<CommentsDto> commentsDtos = commentsService.getAll(filter, pageable);
        return new PagedModel<>(commentsDtos);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном комментарии", description = "В ответе возвращается объект CommentsDto.")
    public CommentsDto getOne(@Schema(description = "ID комментария") @RequestParam UUID id) {
        return commentsService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать комментарий", description = "В ответе возвращается объект CommentsDto.")
    public CommentsDto create(
            @Schema(description = "Содержание комментария") @RequestParam String content,
            @Schema(description = "ID Карточки документа комментария") @RequestParam String docCardId) {
        return commentsService.create(content, docCardId);
    }

    @PatchMapping
    @Operation(summary = "Изменить комментарий", description = "В ответе возвращается объект CommentsDto")
    public CommentsDto patch(@Schema(description = "ID комментария") @RequestParam UUID id,
                             @Schema(description = "Содержание комментария") @RequestParam String content) throws IOException {
        return commentsService.patch(id, content);
    }

    @DeleteMapping
    @Operation(summary = "Удалить комментарий", description = "В ответе возвращается объект CommentsDto")
    public CommentsDto delete(@Schema(description = "ID комментария") @RequestParam UUID id) {
        return commentsService.delete(id);
    }

}
