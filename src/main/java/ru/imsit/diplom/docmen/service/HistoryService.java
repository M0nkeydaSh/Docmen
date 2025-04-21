package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.HistoryDto;
import ru.imsit.diplom.docmen.entity.History;
import ru.imsit.diplom.docmen.enums.StatesEnum;
import ru.imsit.diplom.docmen.filter.HistoryFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.HistoryMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.repository.HistoryRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HistoryService {

    private final UserInfoHelper userInfoHelper;

    private final DocCardRepository docCardRepository;

    private final HistoryMapper historyMapper;

    private final HistoryRepository historyRepository;

    public Page<HistoryDto> getAll(HistoryFilter filter, Pageable pageable) {
        Specification<History> spec = filter.toSpecification();
        Page<History> histories = historyRepository.findAll(spec, pageable);
        return histories.map(historyMapper::toHistoryDto);
    }

    public HistoryDto getOne(UUID id) {
        Optional<History> historyOptional = historyRepository.findById(id);
        return historyMapper.toHistoryDto(historyOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public HistoryDto create(UUID docCardId, String state) {
        var docCard = docCardRepository.findById(docCardId).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        var user = userInfoHelper.getUser();
        var history = History.builder().docCard(docCard).user(user).state(StatesEnum.valueOf(state)).build();
        return historyMapper.toHistoryDto(historyRepository.save(history));

    }

    public HistoryDto patch(UUID id, String docCardName,  String state) throws IOException {
        var history = historyRepository.findById(id).orElseThrow(() -> new RuntimeException("История не найдена"));
        var docCard = docCardRepository.findByName(docCardName).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        history.setDocCard(docCard);
        history.setState(StatesEnum.valueOf(state));
        return historyMapper.toHistoryDto(historyRepository.save(history));
    }


    public HistoryDto delete(UUID id) {
        History history = historyRepository.findById(id).orElseThrow(() -> new RuntimeException("История не найдена"));
        if (history != null) {
            historyRepository.delete(history);
        }
        return historyMapper.toHistoryDto(history);
    }

}
