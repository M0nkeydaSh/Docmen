package ru.imsit.diplom.docmen.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.RouteStepDto;
import ru.imsit.diplom.docmen.entity.RouteStep;
import ru.imsit.diplom.docmen.enums.RouteStepStatesEnum;
import ru.imsit.diplom.docmen.filter.RouteStepFilter;
import ru.imsit.diplom.docmen.mapper.RouteStepMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.repository.RouteStepRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RouteStepService {

    private final RouteStepMapper routeStepMapper;

    private final RouteStepRepository routeStepRepository;

    private final DocCardRepository docCardRepository;

    public Page<RouteStepDto> getAll(RouteStepFilter filter, Pageable pageable) {
        Specification<RouteStep> spec = filter.toSpecification();
        Page<RouteStep> routeSteps = routeStepRepository.findAll(spec, pageable);
        return routeSteps.map(routeStepMapper::toRouteStepDto);
    }

    public RouteStepDto getOne(UUID id) {
        Optional<RouteStep> routeStepOptional = routeStepRepository.findById(id);
        return routeStepMapper.toRouteStepDto(routeStepOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public RouteStepDto create(String docCardId, String numberOfStep, String routeStepState) {
        var docCard = docCardRepository.findById(UUID.fromString(docCardId)).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        var routeStep = RouteStep.builder()
                .docCard(docCard)
                .numberOfStep(numberOfStep)
                .routeStepState(RouteStepStatesEnum.getState(routeStepState))
                .build();
        return routeStepMapper.toRouteStepDto(routeStepRepository.save(routeStep));
    }

    public RouteStepDto patch(UUID id, String docCardId, String numberOfStep, String routeStepState) {
        var docCard = docCardRepository.findById(UUID.fromString(docCardId)).orElseThrow(() -> new RuntimeException("Карточка документа не найдена"));
        var routeStep = routeStepRepository.findById(id).orElseThrow(() -> new RuntimeException("Шаг маршрута не найден"));
        routeStep.setDocCard(docCard);
        routeStep.setNumberOfStep(numberOfStep);
        routeStep.setRouteStepState(RouteStepStatesEnum.valueOf(routeStepState));
        routeStep.setChangeDate(LocalDateTime.now());
        return routeStepMapper.toRouteStepDto(routeStepRepository.save(routeStep));
    }

    public RouteStepDto delete(UUID id) {
        RouteStep routeStep = routeStepRepository.findById(id).orElseThrow(() -> new RuntimeException("Шаг маршрута не найден"));
        if (routeStep != null) {
            routeStepRepository.delete(routeStep);
        }
        return routeStepMapper.toRouteStepDto(routeStep);
    }

    /**
     * Получить шаги маршрута DocCard по его id
     * @param docCardId id документа
     * @return все шаги маршрута данного документа
     */
    public List<RouteStepDto> getStepsByDocCardId(UUID docCardId) {
        List<RouteStep> routeStepList = routeStepRepository.findAllByDocCardIdOrderByNumberOfStep(docCardId);
        return routeStepList.stream().map(routeStepMapper::toRouteStepDto).collect(Collectors.toList());
    }

    public RouteStep findById(UUID routeStepId) {
      return routeStepRepository.findById(routeStepId).orElseThrow(() -> new RuntimeException("Маршрут шага документа не найден"));
    }
}
