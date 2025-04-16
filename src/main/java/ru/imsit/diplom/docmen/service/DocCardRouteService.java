package ru.imsit.diplom.docmen.service;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.imsit.diplom.docmen.dto.DocCardRouteDto;
import ru.imsit.diplom.docmen.entity.DocCardRoute;
import ru.imsit.diplom.docmen.filter.DocCardRouteFilter;
import ru.imsit.diplom.docmen.mapper.DocCardRouteMapper;
import ru.imsit.diplom.docmen.repository.DocCardRouteRepository;
import ru.imsit.diplom.docmen.repository.RouteStepCostumersRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocCardRouteService {

    private final DocCardRouteRepository docCardRouteRepository;

    private final RouteStepCostumersRepository routeStepCostumersRepository;

    private final DocCardRouteMapper docCardRouteMapper;


    public Page<DocCardRouteDto> getAll(DocCardRouteFilter filter, Pageable pageable) {
        Specification<DocCardRoute> spec = filter.toSpecification();
        Page<DocCardRoute> docCardRoutes = docCardRouteRepository.findAll(spec, pageable);
        return docCardRoutes.map(docCardRouteMapper::toDocCardRouteDto);
    }

    public DocCardRouteDto getOne(UUID id) {
        Optional<DocCardRoute> docCardRouteOptional = docCardRouteRepository.findById(id);
        return docCardRouteMapper.toDocCardRouteDto(docCardRouteOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))));
    }

    public DocCardRouteDto delete(UUID id) {
        DocCardRoute docCardRoute = docCardRouteRepository.findById(id).orElse(null);
        if (docCardRoute != null) {
            docCardRouteRepository.delete(docCardRoute);
        }
        return docCardRouteMapper.toDocCardRouteDto(docCardRoute);
    }

    public DocCardRouteDto setReady(UUID id) {
        var docCardRoute = docCardRouteRepository.findById(id).orElseThrow(() -> new RuntimeException("Маршрут документа не найден"));
        docCardRoute.setReady("Y");
        docCardRouteRepository.save(docCardRoute);
        //Получить RouteStep
        //Получит по RouteStep все визы и их статусы и проверить на статус "Y"
        //если все визы "Y" тогда мы документ должны продвинуть на следующий шаг маршрута
        //если нет следующего шага маршрута значит завершить работу с документом проставив статус завершен

        //Если есть то мы его продвигаем на следующий шаг
        //По аналогии со стартом машрута мы получаем все шаги находим следубщий шаг после текущего
        //109 строка добавить логику + повторить используя найденый шаг
        return docCardRouteMapper.toDocCardRouteDto(docCardRouteRepository.save(docCardRoute));
    }

    public DocCardRouteDto setUnready(UUID id) {
        var docCardRoute = docCardRouteRepository.findById(id).orElseThrow(() -> new RuntimeException("Маршрут документа не найден"));
        //откатить документ до статуса черновик и почистить таблицу доккард роут удалить всю инфу относящуюся к маршруту документа
        //Получить RouteStep
        //и пройдя по всем шагам мы должны удалить все элементы докКардРоута относящиеся ко всем шагам маршрута которые документ прошел
        return docCardRouteMapper.toDocCardRouteDto(docCardRouteRepository.save(docCardRoute));
    }

    public DocCardRouteDto create(UUID routeStepCostumerId, String dateComplete) {
        var routeStepCostumer = routeStepCostumersRepository.findById(routeStepCostumerId).orElseThrow(() -> new RuntimeException("Маршрут документа не найден"));
        var docCardRoute = DocCardRoute.builder().routeStepCostumers(routeStepCostumer).ready("N").dateComplete(dateComplete).build();
        return docCardRouteMapper.toDocCardRouteDto(docCardRouteRepository.save(docCardRoute));
    }

    public DocCardRouteDto patch(UUID id, UUID routeStepCostumerId, String dateComplete) {
        var routeStepCostumer = routeStepCostumersRepository.findById(routeStepCostumerId).orElseThrow(() -> new RuntimeException("Шаг пользователя маршрута не найден"));
        var docCardRoute = docCardRouteRepository.findById(id).orElseThrow(() -> new RuntimeException("Маршрут документа не найден"));
        docCardRoute.setRouteStepCostumers(routeStepCostumer);
        docCardRoute.setDateComplete(dateComplete);
        return docCardRouteMapper.toDocCardRouteDto(docCardRouteRepository.save(docCardRoute));
    }




}
