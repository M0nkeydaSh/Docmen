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
import ru.imsit.diplom.docmen.entity.TypeDocument;
import ru.imsit.diplom.docmen.filter.TypeDocumentFilter;
import ru.imsit.diplom.docmen.repository.TypeDocumentRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TypeDocumentService {

    private final TypeDocumentRepository typeDocumentRepository;

    private final ObjectMapper objectMapper;

    public Page<TypeDocument> getAll(TypeDocumentFilter filter, Pageable pageable) {
        Specification<TypeDocument> spec = filter.toSpecification();
        return typeDocumentRepository.findAll(spec, pageable);
    }

    public TypeDocument getOne(UUID id) {
        Optional<TypeDocument> typeDocumentOptional = typeDocumentRepository.findById(id);
        return typeDocumentOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));
    }

    public List<TypeDocument> getMany(List<UUID> ids) {
        return typeDocumentRepository.findAllById(ids);
    }

    public TypeDocument create(TypeDocument typeDocument) {
        return typeDocumentRepository.save(typeDocument);
    }

    public TypeDocument patch(UUID id, JsonNode patchNode) throws IOException {
        TypeDocument typeDocument = typeDocumentRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(typeDocument).readValue(patchNode);

        return typeDocumentRepository.save(typeDocument);
    }

    public List<UUID> patchMany(List<UUID> ids, JsonNode patchNode) throws IOException {
        Collection<TypeDocument> typeDocuments = typeDocumentRepository.findAllById(ids);

        for (TypeDocument typeDocument : typeDocuments) {
            objectMapper.readerForUpdating(typeDocument).readValue(patchNode);
        }

        List<TypeDocument> resultTypeDocuments = typeDocumentRepository.saveAll(typeDocuments);
        return resultTypeDocuments.stream()
                .map(TypeDocument::getId)
                .toList();
    }

    public TypeDocument delete(UUID id) {
        TypeDocument typeDocument = typeDocumentRepository.findById(id).orElse(null);
        if (typeDocument != null) {
            typeDocumentRepository.delete(typeDocument);
        }
        return typeDocument;
    }

    public void deleteMany(List<UUID> ids) {
        typeDocumentRepository.deleteAllById(ids);
    }
}
