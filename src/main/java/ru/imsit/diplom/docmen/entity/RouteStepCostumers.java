package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "route_step_costumers")
@NoArgsConstructor
@AllArgsConstructor
public class RouteStepCostumers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "route_step_id")
    private  RouteStep routeStep;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costumer_id")
    private Costumers costumers;

    @Column(name = "ready")
    private String ready;

    @Column(name = "control_date")
    private String dateTime;

    @Column(name = "change_date")
    private String changeDate;

}