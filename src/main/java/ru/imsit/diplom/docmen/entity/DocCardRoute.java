package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.imsit.diplom.docmen.model.AuditEntity;

@Getter
@Setter
@Entity
@Builder
@Table(name = "doc_card_route")
@NoArgsConstructor
@AllArgsConstructor
public class DocCardRoute extends AuditEntity {

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

}