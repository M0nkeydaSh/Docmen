package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.imsit.diplom.docmen.dto.TypeDocumentDto;
import ru.imsit.diplom.docmen.entity.TypeDocument;
import ru.imsit.diplom.docmen.filter.TypeDocumentFilter;
import ru.imsit.diplom.docmen.mapper.TypeDocumentMapper;
import ru.imsit.diplom.docmen.repository.TypeDocumentRepository;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TypeDocumentService {

    private final TypeDocumentMapper typeDocumentMapper;

    private final TypeDocumentRepository typeDocumentRepository;

    public Page<TypeDocumentDto> getAll(TypeDocumentFilter filter, Pageable pageable) {
        Specification<TypeDocument> spec = filter.toSpecification();
        Page<TypeDocument> typeDocument = typeDocumentRepository.findAll(spec, pageable);
        return typeDocument.map(typeDocumentMapper::toTypeDocumentDto);
    }

    public TypeDocumentDto getOne(UUID id) {
        return typeDocumentMapper.toTypeDocumentDto(typeDocumentRepository.findById(id).orElseThrow(() -> new RuntimeException("Тип документа не найден")));
    }


    public TypeDocumentDto create(String name) {
        return typeDocumentMapper.toTypeDocumentDto(typeDocumentRepository.save(TypeDocument.builder().name(name).build()));
    }

    public TypeDocumentDto patch(UUID id, String name) throws IOException {
        var typeDocument = typeDocumentRepository.findById(id).orElseThrow(() -> new RuntimeException("Тип документа не найден"));
        typeDocument.setName(name);
        return typeDocumentMapper.toTypeDocumentDto(typeDocumentRepository.save(typeDocument));
    }

    public TypeDocumentDto delete(UUID id) {
        TypeDocument typeDocument = typeDocumentRepository.findById(id).orElseThrow(() -> new RuntimeException("Тип документа не найден"));
        if (typeDocument != null) {
            typeDocumentRepository.delete(typeDocument);
        }
        return typeDocumentMapper.toTypeDocumentDto(typeDocument);
    }
}

