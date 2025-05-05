package ru.imsit.diplom.docmen.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
@AllArgsConstructor
public enum StatesEnum {

        DRAFT ("Черновик"),
        PENDING ("На рассмотрении"),
        AWAITING_APPROVAL("Согласование"),
        END_OF_APPROVAL("Согласован"),
        AWAITING_SIGNATURE ("Подписание"),
        END_OF_SIGNATURE ("Подписан"),
        CORRECTED ("Исправлен"),
        AWAITING_APPROVED ("Утверждение"),
        APPROVED ("Утвержден"),
        AWAITING_VIEW("Ознакомление"),
        AWAITING_PUBLISH("Публикация"),
        END_OF_PUBLISHED ("Опубликован"),
        REJECTED ("Отклонен"),
        COMPLETED ("Завершен"),
        ARCHIVED ("Архивирован");

        private final String state;

        @Override
        public String toString() {
                return state;
        }

        public static StatesEnum getStateEng(String stateValue) {
                if (stateValue != null) {
                        stateValue = StatesEnum.valueOf(stateValue).state;
                        var finalStateValue = stateValue;
                        return Stream.of(StatesEnum.values())
                                .filter(statesEnum -> statesEnum.state.equals(finalStateValue))
                                .findFirst()
                                .orElse(StatesEnum.DRAFT);
                } else {
                        return StatesEnum.DRAFT;
                }
        }

        public static StatesEnum getStateRus(String stateValue) {
                if (stateValue != null) {
                        return Stream.of(StatesEnum.values())
                                .filter(statesEnum -> statesEnum.state.equals(stateValue))
                                .findFirst()
                                .orElse(StatesEnum.DRAFT);
                } else {
                        return StatesEnum.DRAFT;
                }
        }

    }

