package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @GetMapping
    public PagedModel<CommentsDto> getAll(@ParameterObject @ModelAttribute CommentsFilter filter, @ParameterObject Pageable pageable) {
        Page<CommentsDto> commentsDtos = commentsService.getAll(filter, pageable);
        return new PagedModel<>(commentsDtos);
    }

    @GetMapping("/{id}")
    public CommentsDto getOne(@PathVariable UUID id) {
        return commentsService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<CommentsDto> getMany(@RequestParam List<UUID> ids) {
        return commentsService.getMany(ids);
    }

    @PostMapping
    public CommentsDto create(@RequestBody CommentsDto dto) {
        return commentsService.create(dto);
    }

    @PatchMapping("/{id}")
    public CommentsDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return commentsService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return commentsService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public CommentsDto delete(@PathVariable UUID id) {
        return commentsService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        commentsService.deleteMany(ids);
    }
}
