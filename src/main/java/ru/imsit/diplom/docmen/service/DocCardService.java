package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.entity.DocCard;
import ru.imsit.diplom.docmen.filter.DocCardFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.DocCardMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.repository.TypeDocumentRepository;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DocCardService {

    private final DocCardMapper docCardMapper;

    private final UserInfoHelper userInfoHelper;

    private final DocCardRepository docCardRepository;

    private final TypeDocumentRepository typeDocumentRepository;

    public Page<DocCardDto> getAll(DocCardFilter filter, Pageable pageable) {
        Specification<DocCard> spec = filter.toSpecification();
        Page<DocCard> docCards = docCardRepository.findAll(spec, pageable);
        return docCards.map(docCardMapper::toDocCardDto);
    }

    public DocCardDto getOne(String name) {
        Optional<DocCard> docCardOptional = docCardRepository.findByName(name);
        return docCardMapper.toDocCardDto(docCardOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(name))));
    }

    public DocCardDto create(String name, String description, String typeDocumentName, String regNum, String keyWords) {
        var typeDocument = typeDocumentRepository.findByName(typeDocumentName);
        var user = userInfoHelper.getUser();
        var docCard = DocCard.builder().name(name).description(description).user(user).typeDocument(typeDocument.orElseThrow()).regNum(regNum).keyWords(keyWords).build();
        return docCardMapper.toDocCardDto(docCardRepository.save(docCard));
    }

    public DocCardDto patch(String name, String description, String typeDocumentName, String regNum, String keyWords) throws IOException {
        var docCard = docCardRepository.findByName(name);
        var typeDocument = typeDocumentRepository.findByName(typeDocumentName);
        docCard.ifPresent(value -> value.setName(name));
        docCard.ifPresent(value -> value.setTypeDocument(typeDocument.orElseThrow()));
        docCard.ifPresent(value -> value.setDescription(description));
        docCard.ifPresent(value -> value.setRegNum(regNum));
        docCard.ifPresent(value -> value.setKeyWords(keyWords));
        return docCardMapper.toDocCardDto(docCardRepository.save(docCard.orElseThrow()));
    }


    public DocCardDto delete(String name) {
        DocCard docCard = docCardRepository.findByName(name).orElse(null);
        if (docCard != null) {
            docCardRepository.delete(docCard);
        }
        return docCardMapper.toDocCardDto(docCard);
    }


}
