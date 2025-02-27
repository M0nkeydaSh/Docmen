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
import ru.imsit.diplom.docmen.dto.DocCardDto;
import ru.imsit.diplom.docmen.entity.DocCard;
import ru.imsit.diplom.docmen.filtr.DocCardFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.DocCardMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocCardService {

    private final DocCardMapper docCardMapper;

    private final UserInfoHelper userInfoHelper;

    private final DocCardRepository docCardRepository;

    private final ObjectMapper objectMapper;

    public Page<DocCardDto> getAll(DocCardFilter filter, Pageable pageable) {
        Specification<DocCard> spec = filter.toSpecification();
        Page<DocCard> docCards = docCardRepository.findAll(spec, pageable);
        return docCards.map(docCardMapper::toDocCardDto);
    }

    public DocCardDto getOne(UUID id) {
        Optional<DocCard> docCardOptional = docCardRepository.findById(id);
        return docCardMapper.toDocCardDto(docCardOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public List<DocCardDto> getMany(List<UUID> ids) {
        List<DocCard> docCards = docCardRepository.findAllById(ids);
        return docCards.stream()
                .map(docCardMapper::toDocCardDto)
                .toList();
    }

    public DocCardDto create(DocCardDto dto) {
        DocCard docCard = docCardMapper.toEntity(dto);
        docCard.setUser(userInfoHelper.getUser());
        DocCard resultDocCard = docCardRepository.save(docCard);
        return docCardMapper.toDocCardDto(resultDocCard);
    }

    public DocCardDto patch(UUID id, JsonNode patchNode) throws IOException {
        DocCard docCard = docCardRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        DocCardDto docCardDto = docCardMapper.toDocCardDto(docCard);
        objectMapper.readerForUpdating(docCardDto).readValue(patchNode);
        docCardMapper.updateWithNull(docCardDto, docCard);

        DocCard resultDocCard = docCardRepository.save(docCard);
        return docCardMapper.toDocCardDto(resultDocCard);
    }

    public List<UUID> patchMany(List<UUID> ids, JsonNode patchNode) throws IOException {
        Collection<DocCard> docCards = docCardRepository.findAllById(ids);

        for (DocCard docCard : docCards) {
            DocCardDto docCardDto = docCardMapper.toDocCardDto(docCard);
            objectMapper.readerForUpdating(docCardDto).readValue(patchNode);
            docCardMapper.updateWithNull(docCardDto, docCard);
        }

        List<DocCard> resultDocCards = docCardRepository.saveAll(docCards);
        return resultDocCards.stream()
                .map(DocCard::getId)
                .toList();
    }

    public DocCardDto delete(UUID id) {
        DocCard docCard = docCardRepository.findById(id).orElse(null);
        if (docCard != null) {
            docCardRepository.delete(docCard);
        }
        return docCardMapper.toDocCardDto(docCard);
    }

    public void deleteMany(List<UUID> ids) {
        docCardRepository.deleteAllById(ids);
    }
}
