package ru.imsit.diplom.docmen.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;
import ru.imsit.diplom.docmen.entity.DocCardRoute;
import ru.imsit.diplom.docmen.filter.DocCardRouteFilter;
import ru.imsit.diplom.docmen.service.DocCardRouteService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/rest/admin-ui/docCardRoutes")
@RequiredArgsConstructor
public class DocCardRouteController {

    private final DocCardRouteService docCardRouteService;

    @GetMapping
    public PagedModel<DocCardRoute> getAll(@ParameterObject @ModelAttribute DocCardRouteFilter filter, @ParameterObject Pageable pageable) {
        Page<DocCardRoute> docCardRoutes = docCardRouteService.getAll(filter, pageable);
        return new PagedModel<>(docCardRoutes);
    }

    @GetMapping("/{id}")
    public DocCardRoute getOne(@PathVariable UUID id) {
        return docCardRouteService.getOne(id);
    }

    @PostMapping
    public DocCardRoute create(@RequestBody DocCardRoute docCardRoute) {
        return docCardRouteService.create(docCardRoute);
    }

    @PatchMapping("/{id}")
    public DocCardRoute patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return docCardRouteService.patch(id, patchNode);
    }

    @PostMapping("/setReady/{id}")
    public DocCardRoute setReady(@PathVariable UUID id) throws IOException {
        return docCardRouteService.setReady(id);
    }

    @PostMapping("/setUnready/{id}")
    public DocCardRoute setUnready(@PathVariable UUID id) throws IOException {
        return docCardRouteService.setUnready(id);
    }

    @DeleteMapping("/{id}")
    public DocCardRoute delete(@PathVariable UUID id) {
        return docCardRouteService.delete(id);
    }


}
