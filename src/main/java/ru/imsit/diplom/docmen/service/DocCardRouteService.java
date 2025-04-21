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
import ru.imsit.diplom.docmen.enums.StatesEnum;
import ru.imsit.diplom.docmen.filter.DocCardRouteFilter;
import ru.imsit.diplom.docmen.helper.UserInfoHelper;
import ru.imsit.diplom.docmen.mapper.DocCardRouteMapper;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.repository.DocCardRouteRepository;
import ru.imsit.diplom.docmen.repository.RouteStepCostumersRepository;
import ru.imsit.diplom.docmen.repository.RouteStepRepository;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class DocCardRouteService {

    private final DocCardRouteRepository docCardRouteRepository;

    private final DocCardRepository docCardRepository;

    private final RouteStepCostumersRepository routeStepCostumersRepository;

    private final RouteStepRepository routeStepRepository;

    private final DocCardRouteMapper docCardRouteMapper;

    private final RouteStepCostumersService routeStepCostumersService;

    private final RouteStepService routeStepService;

    private final CommentsService commentsService;

    private final UserInfoHelper userInfoHelper;


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
        DocCardRoute docCardRoute = docCardRouteRepository.findById(id).orElseThrow(() -> new RuntimeException("Карточка карточки документа не найден"));
        if (docCardRoute != null) {
            docCardRouteRepository.delete(docCardRoute);
        }
        return docCardRouteMapper.toDocCardRouteDto(docCardRoute);
    }

    public DocCardRouteDto setReady(UUID id) {
        var docCardRoute = docCardRouteRepository.findById(id).orElseThrow(() -> new RuntimeException("Маршрут карточки документа не найден"));
        docCardRoute.setReady("Y");
        var result = docCardRouteRepository.save(docCardRoute);
        //Получить RouteStep
        var routeStep = docCardRoute.getRouteStep();
        //Получить по RouteStep карточку документа
        var currentDocCard = routeStep.getDocCard();
        //Получить ID текущего документа
        UUID currentDocCardId = currentDocCard.getId();
        //Получить все шаги маршрута документа
        var routeSteps = routeStepService.getStepsByDocCardId(currentDocCardId);
        //Получить кол-во шагов маршрута документа
        var countSteps = routeSteps.size();
        //Получить по RouteStep все визы и их статусы и проверить на статус "Y"
        var routeStepCostumers = routeStepCostumersService.getAllUsersByRouteStepId((routeStep.getId()));
        boolean allVisasApproved = routeStepCostumers.stream().allMatch(ready -> ready.getReady().equals("Y"));
        if (allVisasApproved) {
            //если все визы "Y" тогда мы должны продвинуть документ на следующий шаг маршрута
            //если нет следующего шага маршрута значит завершить работу с документом проставив статус завершен
            //Если есть, то мы его продвигаем на следующий шаг
            var routeStepIndex = -1;
            for (var routeStepDto : routeSteps) {
                if (routeStepDto.getId().equals(routeStep.getId().toString())) {
                    routeStepIndex = routeSteps.indexOf(routeStepDto);
                }
            }

            if (routeStepIndex == (countSteps - 1)) { //поменять первый аргумент
                currentDocCard.setState(StatesEnum.valueOf("COMPLETED"));
                docCardRepository.save(currentDocCard);
            } else {
                //Получить следующий шаг после текущего шага маршрута документа
                var nextStep = routeSteps.get(routeStepIndex + 1);
                //Выставить статус равный статусу шага маршрут
                userInfoHelper.startRouteStep(currentDocCard, nextStep);
            }
        }
        return docCardRouteMapper.toDocCardRouteDto(result);
    }

    public DocCardRouteDto setUnready(UUID id, String comment) {
        var docCardRoute = docCardRouteRepository.findById(id).orElseThrow(() -> new RuntimeException("Маршрут документа не найден"));
        //откатить документ до статуса черновик и почистить таблицу docCardRoute удалить всю инфу относящуюся к маршруту документа
        //получить RouteStep
        var routeStep = docCardRoute.getRouteStep();

        //Получить по RouteStep карточку документа
        var curentDocCard = routeStep.getDocCard();

        if (!comment.isEmpty()) {
            commentsService.create(comment, String.valueOf(curentDocCard.getId()));
        }

        //Изменить статус документа на черновик
        curentDocCard.setState(StatesEnum.valueOf("DRAFT"));

        // Сохраняем обновленный DocCard
        docCardRepository.save(curentDocCard);

        //Получить ID текущего документа
        UUID currentDocCardId = curentDocCard.getId();

        //Получить все шаги маршрута документа
        var routeSteps = routeStepService.getStepsByDocCardId(currentDocCardId);

        //и пройдя по всем шагам мы должны удалить все элементы докКардРоута относящиеся ко всем шагам маршрута которые документ прошел
        for (var routeStepDto : routeSteps) {

            // Удаляем все элементы DocCardRoute, относящиеся к маршруту документа
            docCardRouteRepository.deleteAllByRouteStep_Id(UUID.fromString(routeStepDto.getId()));

        }


        return docCardRouteMapper.toDocCardRouteDto(docCardRouteRepository.save(docCardRoute));
    }

    public DocCardRouteDto create(UUID routeStepCostumerId, String dateComplete, UUID routeStepId) {
        var routeStepCostumer = routeStepCostumersRepository.findById(routeStepCostumerId).orElseThrow(() -> new RuntimeException("Маршрут шага пользователя не найден"));
        var routeStep = routeStepRepository.findById(routeStepId).orElseThrow(() -> new RuntimeException("Маршрут шага документа не найден"));
        var docCardRoute = DocCardRoute.builder().routeStepCostumers(routeStepCostumer).routeStep(routeStep).ready("N").dateComplete(dateComplete).build();
        return docCardRouteMapper.toDocCardRouteDto(docCardRouteRepository.save(docCardRoute));
    }

    public DocCardRouteDto patch(UUID id, UUID routeStepCostumerId, UUID routeStepId, String dateComplete) {
        var routeStepCostumer = routeStepCostumersRepository.findById(routeStepCostumerId).orElseThrow(() -> new RuntimeException("Маршрут шага пользователя не найден"));
        var routeStep = routeStepRepository.findById(routeStepId).orElseThrow(() -> new RuntimeException("Маршрут шага документа не найден"));
        var docCardRoute = docCardRouteRepository.findById(id).orElseThrow(() -> new RuntimeException("Маршрут карточки документа не найден"));
        docCardRoute.setRouteStepCostumers(routeStepCostumer);
        docCardRoute.setRouteStep(routeStep);
        docCardRoute.setDateComplete(dateComplete);
        return docCardRouteMapper.toDocCardRouteDto(docCardRouteRepository.save(docCardRoute));
    }


}
