package ru.imsit.diplom.docmen.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.RouteStepCostumersDto;
import ru.imsit.diplom.docmen.entity.RouteStepCostumers;
import ru.imsit.diplom.docmen.filter.RouteStepCostumersFilter;
import ru.imsit.diplom.docmen.mapper.RouteStepCostumersMapper;
import ru.imsit.diplom.docmen.repository.CostumersRepository;
import ru.imsit.diplom.docmen.repository.RouteStepCostumersRepository;
import ru.imsit.diplom.docmen.repository.RouteStepRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RouteStepCostumersService {

    private final RouteStepCostumersMapper routeStepCostumersMapper;

    private final RouteStepCostumersRepository routeStepCostumersRepository;

    private final ObjectMapper objectMapper;

    private final RouteStepRepository routeStepRepository;

    private final CostumersRepository costumersRepository;

    public Page<RouteStepCostumersDto> getAll(RouteStepCostumersFilter filter, Pageable pageable) {
        Specification<RouteStepCostumers> spec = filter.toSpecification();
        Page<RouteStepCostumers> routeStepParticipants = routeStepCostumersRepository.findAll(spec, pageable);
        return routeStepParticipants.map(routeStepCostumersMapper::toRouteStepParticipantsDto);
    }

    public RouteStepCostumersDto getOne(UUID id) {
        Optional<RouteStepCostumers> routeStepParticipantsOptional = routeStepCostumersRepository.findById(id);
        return routeStepCostumersMapper.toRouteStepParticipantsDto(routeStepParticipantsOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }



    public RouteStepCostumersDto create(String routeStepId, String costumersId, String ready, String dateTime) {
        var routeStep = routeStepRepository.findById(UUID.fromString(routeStepId));
        var costumer = costumersRepository.findById(UUID.fromString(costumersId));
        var routeStepParticipants = RouteStepCostumers.builder().routeStep(routeStep.orElseThrow()).costumers(costumer.orElseThrow()).ready(ready).dateTime(dateTime).build();
        return routeStepCostumersMapper.toRouteStepParticipantsDto(routeStepCostumersRepository.save(routeStepParticipants));
    }

    public RouteStepCostumersDto patch(UUID id, String routeStepId, String costumersId, String ready, String dateTime) {
        var routeStep = routeStepRepository.findById(UUID.fromString(routeStepId));
        var costumer = costumersRepository.findById(UUID.fromString(costumersId));
        var routeStepParticipants = routeStepCostumersRepository.findById(id);
        routeStepParticipants.ifPresent(value -> value.setRouteStep(routeStep.orElseThrow()));
        routeStepParticipants.ifPresent(value -> value.setCostumers(costumer.orElseThrow()));
        routeStepParticipants.ifPresent(value -> value.setReady(ready));
        routeStepParticipants.ifPresent(value -> value.setDateTime(dateTime));
        return routeStepCostumersMapper.toRouteStepParticipantsDto(routeStepCostumersRepository.save(routeStepParticipants.orElseThrow()));
    }

    public RouteStepCostumersDto delete(UUID id) {
        RouteStepCostumers routeStepCostumers = routeStepCostumersRepository.findById(id).orElse(null);
        if (routeStepCostumers != null) {
            routeStepCostumersRepository.delete(routeStepCostumers);
        }
        return routeStepCostumersMapper.toRouteStepParticipantsDto(routeStepCostumers);
    }
}
