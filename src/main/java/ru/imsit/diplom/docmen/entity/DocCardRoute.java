package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "doc_card_route")
@NoArgsConstructor
@AllArgsConstructor
public class DocCardRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_step_costumer_id")
    private RouteStepCostumers routeStepCostumers;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "route_step_id")
    private RouteStep routeStep;

    @Column(name = "ready")
    private String ready;

    @Column(name = "date_complete")
    private String dateComplete;

    @Column(name = "change_date")
    private String changeDate;

}