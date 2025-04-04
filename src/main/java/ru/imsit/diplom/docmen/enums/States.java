package ru.imsit.diplom.docmen.enums;

public enum States {

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

        States(String state) {
        }

    }

