package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "state_card")
public class StateCard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    public enum States{
        DRAFT ("Черновик"),
        PENDING ("На рассмотрении"),
        APPROVED ("Утвержден"),
        REJECTED ("Отклонен"),
        PUBLISHED ("Опубликован"),
        ARCHIVED ("Архивирован"),
        CORRECTED ("Исправлен"),
        COMPLETED ("Завершен"),
        AWAITING_SIGNATURE ("В ожидании подписи"),
        IN_THE_PROCESS_OF_APPROVAL("в процессе согласования");

        States(String state) {
        }
    }

    @Column(name = "change_date")
    private String changeDate;

}