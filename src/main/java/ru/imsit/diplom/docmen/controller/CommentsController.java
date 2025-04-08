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
    @Operation(summary = "Получить данные о всех коментариях", description = "В ответе возвращаются объекты CommentsDto.")
    public PagedModel<CommentsDto> getAll(@ParameterObject @ModelAttribute CommentsFilter filter, @ParameterObject Pageable pageable) {
        Page<CommentsDto> commentsDtos = commentsService.getAll(filter, pageable);
        return new PagedModel<>(commentsDtos);
    }

    @GetMapping("/getOne")
    @Operation(summary = "Получить данные о конкретном коментарии", description = "В ответе возвращается объект CommentsDto.")
    public CommentsDto getOne(@Schema(description = "ID коментария") @RequestParam UUID id) {
        return commentsService.getOne(id);
    }

    @PostMapping
    @Operation(summary = "Создать коментарий", description = "В ответе возвращается объект CommentsDto.")
    public CommentsDto create(
            @Schema(description = "Содержание коментария") @RequestParam String content,
            @Schema(description = "Карточка документа коментария") @RequestParam String docCard) {
        return commentsService.create(content, docCard);
    }

    @PatchMapping
    @Operation(summary = "Изменить коментарий", description = "В ответе возвращается объект CommentsDto")
    public CommentsDto patch(@Schema(description = "ID коментария") @RequestParam UUID id,
                             @Schema(description = "Содержание коментария") @RequestParam String content) throws IOException {
        return commentsService.patch(id, content);
    }

    @DeleteMapping
    @Operation(summary = "Удалить коментарий", description = "В ответе возвращается объект CommentsDto")
    public CommentsDto delete(@Schema(description = "ID коментария") @RequestParam UUID id) {
        return commentsService.delete(id);
    }

}
