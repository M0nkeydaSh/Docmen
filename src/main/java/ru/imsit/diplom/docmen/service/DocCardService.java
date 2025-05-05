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
import ru.imsit.diplom.docmen.enums.StatesEnum;
import ru.imsit.diplom.docmen.filter.DocCardFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.DocCardMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.repository.TypeDocumentRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocCardService {

    private final DocCardMapper docCardMapper;

    private final UserInfoHelper userInfoHelper;

    private final DocCardRepository docCardRepository;

    private final TypeDocumentRepository typeDocumentRepository;

    private final RouteStepService routeStepService;

    private final DocCardRouteService docCardRouteService;

    private final HistoryService historyService;

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

    public DocCardDto create(String name, String description, String typeDocumentName, String regNum, String keyWords, String state) {
        var typeDocument = typeDocumentRepository.findByName(typeDocumentName).orElseThrow(() -> new RuntimeException("Тип документа не найден"));
        var user = userInfoHelper.getUser();
        var docCard = DocCard.builder()
                .name(name)
                .description(description)
                .user(user)
                .state(StatesEnum.getStateRus(state))
                .typeDocument(typeDocument)
                .regNum(regNum)
                .keyWords(keyWords).build();
        docCardMapper.toDocCardDto(docCardRepository.save(docCard));
        var docCardId = docCard.getId();
        historyService.create(docCardId,state);
        return docCardMapper.toDocCardDto(docCardRepository.save(docCard));
    }

    public DocCardDto patch(UUID id, String name, String description, String typeDocumentName, String regNum, String keyWords, String state) throws IOException {
        var docCard = docCardRepository.findById(id).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        var typeDocument = typeDocumentRepository.findByName(typeDocumentName).orElseThrow(() -> new RuntimeException("Тип документа не найден"));
        docCard.setName(name);
        docCard.setTypeDocument(typeDocument);
        docCard.setDescription(description);
        docCard.setRegNum(regNum);
        docCard.setKeyWords(keyWords);
        docCard.setState(StatesEnum.getStateRus(state));
        return docCardMapper.toDocCardDto(docCardRepository.save(docCard));
    }


    public DocCardDto delete(UUID id) {
        DocCard docCard = docCardRepository.findById(id).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        if (docCard != null) {
            docCardRepository.delete(docCard);
        }
        return docCardMapper.toDocCardDto(docCard);
    }

    public void startRoute(UUID docCardId) {
        //запускаем документ по маршруту
        var docCard = docCardRepository.findById(docCardId).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        //получить шаги маршрута документа
        var routeSteps = routeStepService.getStepsByDocCardId(docCardId);
        //проверить есть ли шаги маршрута документа
        if (routeSteps.isEmpty()) {
            throw new RuntimeException("Документ не имеет маршрута");
        }
        //проверить что документ не имеет маршрута
        if (!docCard.getState().equals(StatesEnum.DRAFT)) {
            throw new RuntimeException("Документ уже имеет маршрут");
        }
        var firstStep = routeSteps.get(0);
        //Выставить статус равный статусу шага маршрута
        docCardRouteService.startRouteStep(docCard, firstStep);

    }

}
