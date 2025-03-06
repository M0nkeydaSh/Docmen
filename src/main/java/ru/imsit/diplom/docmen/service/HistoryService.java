package ru.imsit.diplom.docmen.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.HistoryDto;
import ru.imsit.diplom.docmen.entity.History;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.HistoryMapper;
import ru.imsit.diplom.docmen.repository.HistoryRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HistoryService {

    private final UserInfoHelper userInfoHelper;

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

    public List<HistoryDto> getMany(List<UUID> ids) {
        List<History> histories = historyRepository.findAllById(ids);
        return histories.stream()
                .map(historyMapper::toHistoryDto)
                .toList();
    }

    public HistoryDto create(HistoryDto dto) {
        History history = historyMapper.toEntity(dto);
        History resultHistory = historyRepository.save(history);
        return historyMapper.toHistoryDto(resultHistory);
    }

    public HistoryDto patch(UUID id, JsonNode patchNode) throws IOException {
        History history = historyRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        HistoryDto historyDto = historyMapper.toHistoryDto(history);
        objectMapper.readerForUpdating(historyDto).readValue(patchNode);
        historyMapper.updateWithNull(historyDto, history);

        History resultHistory = historyRepository.save(history);
        return historyMapper.toHistoryDto(resultHistory);
    }


    public HistoryDto delete(UUID id) {
        History history = historyRepository.findById(id).orElse(null);
        if (history != null) {
            historyRepository.delete(history);
        }
        return historyMapper.toHistoryDto(history);
    }

    public void deleteMany(List<UUID> ids) {
        historyRepository.deleteAllById(ids);
    }
}
