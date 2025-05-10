package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.imsit.diplom.docmen.model.AuditEntity;

@Getter
@Setter
@Entity
@Builder
@Table(name = "route_step_costumers")
@NoArgsConstructor
@AllArgsConstructor
public class RouteStepCostumers extends AuditEntity {

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "route_step_id")
    private  RouteStep routeStep;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "costumer_id")
    private Costumers costumers;

    @Column(name = "ready")
    private String ready;

    @Column(name = "control_date")
    private String controlDate;

}