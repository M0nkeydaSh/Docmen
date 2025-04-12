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

        public static StatesEnum getState(String state) {
                if (state != null) {
                        return Stream.of(StatesEnum.values())
                                .filter(statesEnum -> statesEnum.state.equals(state))
                                .findFirst()
                                .orElse(StatesEnum.DRAFT);
                } else {
                        return StatesEnum.DRAFT;
                }
        }

    }

