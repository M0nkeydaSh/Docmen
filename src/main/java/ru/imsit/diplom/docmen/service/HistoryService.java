package ru.imsit.diplom.docmen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.HistoryDto;
import ru.imsit.diplom.docmen.entity.History;
import ru.imsit.diplom.docmen.enums.States;
import ru.imsit.diplom.docmen.mapper.HistoryMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.repository.HistoryRepository;
import ru.imsit.diplom.docmen.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HistoryService {

    private final DocCardRepository docCardRepository;

    private final UserRepository userRepository;

    private final HistoryMapper historyMapper;

    private final HistoryRepository historyRepository;

    private final ObjectMapper objectMapper;

    public Page<HistoryDto> getAll(Pageable pageable) {
        Page<History> histories = historyRepository.findAll(pageable);
        return histories.map(historyMapper::toHistoryDto);
    }

    public HistoryDto getOne(UUID id) {
        Optional<History> historyOptional = historyRepository.findById(id);
        return historyMapper.toHistoryDto(historyOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public HistoryDto create(String docCardName, String userName, String state) {
        var docCard = docCardRepository.findByName(docCardName);
        var user = userRepository.findByUsername(userName);
        var history = History.builder().docCard(docCard.orElseThrow()).user(user.orElseThrow()).state(States.valueOf(state)).build();
        return historyMapper.toHistoryDto(historyRepository.save(history));

    }

    public HistoryDto patch(UUID id, String docCardName, String userName, String state) throws IOException {
        var history = historyRepository.findById(id);
        var docCard = docCardRepository.findByName(docCardName);
        var user = userRepository.findByUsername(userName);
        history.ifPresent(value -> value.setDocCard(docCard.orElseThrow()));
        history.ifPresent(value -> value.setUser(user.orElseThrow()));
        history.ifPresent(value -> value.setState(States.valueOf(state)));
        return historyMapper.toHistoryDto(historyRepository.save(history.orElseThrow()));
    }


    public HistoryDto delete(UUID id) {
        History history = historyRepository.findById(id).orElse(null);
        if (history != null) {
            historyRepository.delete(history);
        }
        return historyMapper.toHistoryDto(history);
    }

}
