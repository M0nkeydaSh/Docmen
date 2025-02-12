package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.filtr.DocCardFilter;
import ru.imsit.diplom.docmen.service.DocCardService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/docCards")
@RequiredArgsConstructor
public class DocCardController {

    private final DocCardService docCardService;

    @GetMapping
    public PagedModel<DocCardDto> getAll(@ParameterObject @ModelAttribute DocCardFilter filter, @ParameterObject Pageable pageable) {
        Page<DocCardDto> docCardDtos = docCardService.getAll(filter, pageable);
        return new PagedModel<>(docCardDtos);
    }

    @GetMapping("/{id}")
    public DocCardDto getOne(@PathVariable UUID id) {
        return docCardService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<DocCardDto> getMany(@RequestParam List<UUID> ids) {
        return docCardService.getMany(ids);
    }

    @PostMapping
    public DocCardDto create(@RequestBody DocCardDto dto) {
        return docCardService.create(dto);
    }

    @PatchMapping("/{id}")
    public DocCardDto patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return docCardService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return docCardService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public DocCardDto delete(@PathVariable UUID id) {
        return docCardService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        docCardService.deleteMany(ids);
    }
}
