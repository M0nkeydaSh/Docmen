package ru.imsit.diplom.docmen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum RouteStepStatesEnum {
    AWAITING_APPROVAL("Согласование"),
    AWAITING_SIGNATURE ("Подписание"),
    AWAITING_APPROVED ("Утверждение"),
    AWAITING_VIEW("Ознакомление"),
    AWAITING_PUBLISH("Публикация");

    private final String routeStepState;

    @Override
    public String toString() {
        return routeStepState;
    }



    public static RouteStepStatesEnum getState(String routeStepState) {
        if (routeStepState != null) {
            return Stream.of(RouteStepStatesEnum.values())
                    .filter(routeStepStatesEnum -> routeStepStatesEnum.routeStepState.equals(routeStepState))
                    .findFirst()
                    .orElse(RouteStepStatesEnum.AWAITING_APPROVAL);
        } else {
            return RouteStepStatesEnum.AWAITING_APPROVAL;
        }
    }
}
