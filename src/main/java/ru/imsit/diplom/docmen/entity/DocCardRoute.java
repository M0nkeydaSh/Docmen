package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "doc_card_route")
public class DocCardRoute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "route_step_costumers_id")
    private  RouteStepCostumers routeStepCostumers;

    @Column(name = "ready")
    private String ready;

    @Column(name = "date_complete")
    private String dateComplete;

    @Column(name = "change_date")
    private String changeDate;

}