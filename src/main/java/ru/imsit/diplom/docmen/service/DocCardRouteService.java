package ru.imsit.diplom.docmen.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.entity.DocCardRoute;
import ru.imsit.diplom.docmen.filter.DocCardRouteFilter;
import ru.imsit.diplom.docmen.repository.DocCardRouteRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocCardRouteService {

    private final DocCardRouteRepository docCardRouteRepository;

    private final ObjectMapper objectMapper;

    public Page<DocCardRoute> getAll(DocCardRouteFilter filter, Pageable pageable) {
        Specification<DocCardRoute> spec = filter.toSpecification();
        return docCardRouteRepository.findAll(spec, pageable);
    }

    public DocCardRoute getOne(UUID id) {
        Optional<DocCardRoute> docCardRouteOptional = docCardRouteRepository.findById(id);
        return docCardRouteOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    public DocCardRoute create(DocCardRoute docCardRoute) {
        return docCardRouteRepository.save(docCardRoute);
    }

    public DocCardRoute patch(UUID id, JsonNode patchNode) throws IOException {
        DocCardRoute docCardRoute = docCardRouteRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(docCardRoute).readValue(patchNode);

        return docCardRouteRepository.save(docCardRoute);
    }

    public DocCardRoute delete(UUID id) {
        DocCardRoute docCardRoute = docCardRouteRepository.findById(id).orElse(null);
        if (docCardRoute != null) {
            docCardRouteRepository.delete(docCardRoute);
        }
        return docCardRoute;
    }

    public DocCardRoute setReady(UUID id) {
        var docCardRoute = docCardRouteRepository.findById(id);
        docCardRoute.ifPresent(value -> value.setReady("Y"));
        return null;
    }

    public DocCardRoute setUnready(UUID id) {
        var docCardRoute = docCardRouteRepository.findById(id);
        docCardRoute.ifPresent(value -> value.setReady("N"));
        return null;
    }
}
