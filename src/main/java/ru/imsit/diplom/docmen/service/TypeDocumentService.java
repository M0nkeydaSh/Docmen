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

    public TypeDocumentDto getOne(String name) {
        return typeDocumentMapper.toTypeDocumentDto(typeDocumentRepository.findByName(name).orElseThrow());
    }


    public TypeDocumentDto create(String name) {
        return typeDocumentMapper.toTypeDocumentDto(typeDocumentRepository.save(TypeDocument.builder().name(name).build()));
    }

    public TypeDocumentDto patch(String name, String changeName) throws IOException {
        var typeDocument = typeDocumentRepository.findByName(name);
        typeDocument.ifPresent(u -> u.setName(changeName));
        return typeDocumentMapper.toTypeDocumentDto(typeDocumentRepository.save(typeDocument.orElseThrow()));
    }

    public TypeDocumentDto delete(String name) {
        TypeDocument typeDocument = typeDocumentRepository.findByName(name).orElse(null);
        if (typeDocument != null) {
            typeDocumentRepository.delete(typeDocument);
        }
        return typeDocumentMapper.toTypeDocumentDto(typeDocument);
    }
}

