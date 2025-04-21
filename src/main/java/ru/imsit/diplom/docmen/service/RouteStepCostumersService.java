package ru.imsit.diplom.docmen.service;

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

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RouteStepCostumersService {

    private final RouteStepCostumersMapper routeStepCostumersMapper;

    private final RouteStepCostumersRepository routeStepCostumersRepository;

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
        var routeStep = routeStepRepository.findById(UUID.fromString(routeStepId)).orElseThrow(() -> new RuntimeException("Шаг маршрута не найден"));
        var costumer = costumersRepository.findById(UUID.fromString(costumersId)).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        var routeStepCostumer = RouteStepCostumers.builder()
                .routeStep(routeStep)
                .costumers(costumer)
                .ready(ready)
                .controlDate(dateTime)
                .build();
        return routeStepCostumersMapper.toRouteStepParticipantsDto(routeStepCostumersRepository.save(routeStepCostumer));
    }

    public RouteStepCostumersDto patch(UUID id, String routeStepId, String costumersId, String ready, String dateTime) {
        var routeStep = routeStepRepository.findById(UUID.fromString(routeStepId)).orElseThrow(() -> new RuntimeException("Шаг маршрута не найден"));
        var costumer = costumersRepository.findById(UUID.fromString(costumersId)).orElseThrow(() -> new RuntimeException("Сотрудник не найден"));
        var routeStepCostumer = routeStepCostumersRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь шага маршрута не найден"));
        routeStepCostumer.setRouteStep(routeStep);
        routeStepCostumer.setCostumers(costumer);
        routeStepCostumer.setReady(ready);
        routeStepCostumer.setControlDate(dateTime);
        return routeStepCostumersMapper.toRouteStepParticipantsDto(routeStepCostumersRepository.save(routeStepCostumer));
    }

    public RouteStepCostumersDto delete(UUID id) {
        var routeStepCostumer = routeStepCostumersRepository.findById(id).orElseThrow(() -> new RuntimeException("Пользователь шага маршрута не найден"));
        if (routeStepCostumer != null) {
            routeStepCostumersRepository.delete(routeStepCostumer);
        }
        return routeStepCostumersMapper.toRouteStepParticipantsDto(routeStepCostumer);
    }

    //создать метод получения списка пользователей по маршруту
    public List<RouteStepCostumersDto> getAllUsersByRouteStepId(UUID routeStepId) {
        List<RouteStepCostumers> routeStepCostumersList = routeStepCostumersRepository.findAllByRouteStepId(routeStepId);
        return routeStepCostumersList.stream().map(routeStepCostumersMapper::toRouteStepParticipantsDto).collect(Collectors.toList());
    }

}
