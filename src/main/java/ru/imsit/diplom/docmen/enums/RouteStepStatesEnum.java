package ru.imsit.diplom.docmen.enums;

public enum RouteStepStatesEnum {
    AWAITING_APPROVAL("Согласование"),
    AWAITING_SIGNATURE ("Подписание"),
    AWAITING_APPROVED ("Утверждение"),
    AWAITING_VIEW("Ознакомление"),
    AWAITING_PUBLISH("Публикация");

    RouteStepStatesEnum(String state) {
    }
}
