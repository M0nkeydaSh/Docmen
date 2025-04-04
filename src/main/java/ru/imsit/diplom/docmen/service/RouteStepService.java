package ru.imsit.diplom.docmen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RouteStepService{

    private final RouteStepMapper routeStepMapper;

    private final RouteStepRepository routeStepRepository;

    private final ObjectMapper objectMapper;
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
        var docCard = docCardRepository.findById(UUID.fromString(docCardId));
        var routeStep = RouteStep.builder().docCard(docCard.orElseThrow()).numberOfStep(numberOfStep).routeStepState(RouteStepStatesEnum.valueOf(routeStepState)).build();
        return routeStepMapper.toRouteStepDto(routeStepRepository.save(routeStep));
    }

    public RouteStepDto patch(UUID id, String docCardId, String numberOfStep, String routeStepState) {
        var docCard = docCardRepository.findById(UUID.fromString(docCardId));
        var routeStep = routeStepRepository.findById(id);
        routeStep.ifPresent(value -> value.setDocCard(docCard.orElseThrow()));
        routeStep.ifPresent(value -> value.setNumberOfStep(numberOfStep));
        routeStep.ifPresent(value -> value.setRouteStepState(RouteStepStatesEnum.valueOf(routeStepState)));
        return routeStepMapper.toRouteStepDto(routeStepRepository.save(routeStep.orElseThrow()));
    }

    public RouteStepDto delete(UUID id) {
        RouteStep routeStep = routeStepRepository.findById(id).orElse(null);
        if (routeStep != null) {
            routeStepRepository.delete(routeStep);
        }
        return routeStepMapper.toRouteStepDto(routeStep);
    }
}
