package ru.imsit.diplom.docmen.enums;

public enum States {

        DRAFT ("Черновик"),
        PENDING ("На рассмотрении"),
        IN_THE_PROCESS_OF_APPROVAL("в процессе согласования"),
        AWAITING_SIGNATURE ("В ожидании подписи"),
        CORRECTED ("Исправлен"),
        APPROVED ("Утвержден"),
        PUBLISHED ("Опубликован"),
        REJECTED ("Отклонен"),
        COMPLETED ("Завершен"),
        ARCHIVED ("Архивирован");

        States(String state) {
        }

    }

