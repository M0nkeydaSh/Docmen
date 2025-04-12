package ru.imsit.diplom.docmen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}
