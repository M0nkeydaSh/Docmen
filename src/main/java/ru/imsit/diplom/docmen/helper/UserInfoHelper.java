package ru.imsit.diplom.docmen.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.imsit.diplom.docmen.dto.RouteStepDto;
import ru.imsit.diplom.docmen.entity.DocCard;
import ru.imsit.diplom.docmen.entity.User;
import ru.imsit.diplom.docmen.enums.StatesEnum;
import ru.imsit.diplom.docmen.repository.DocCardRepository;
import ru.imsit.diplom.docmen.repository.UserRepository;
import ru.imsit.diplom.docmen.service.DocCardRouteService;
import ru.imsit.diplom.docmen.service.RouteStepCostumersService;

import java.util.UUID;



@Component
@RequiredArgsConstructor
public class UserInfoHelper {
    private final DocCardRepository docCardRepository;

    private final RouteStepCostumersService routeStepCostumersService;

    private final DocCardRouteService docCardRouteService;

    private final UserRepository userRepository;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new RuntimeException("User is not found"));
        }
        throw  new RuntimeException("User is not logged in");
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User is not found"));
    }

    public void startRouteStep(DocCard docCard, RouteStepDto firstStep) {
        docCard.setState(StatesEnum.valueOf(firstStep.getRouteStepState()));
        docCardRepository.save(docCard);
        //Создать визы маршрута документа
        //получить всех пользователей которые будут выполнять шаги маршрута документа
        var routeStepCostumers = routeStepCostumersService.getAllUsersByRouteStepId(UUID.fromString(firstStep.getId()));
        //Создаем визы маршрута документа
        for (var routeStepCostumer : routeStepCostumers) {
            docCardRouteService.create(UUID.fromString(routeStepCostumer.getId()), routeStepCostumer.getControlDate(), UUID.fromString(routeStepCostumer.getRouteStepId()));
        }
    }

}
