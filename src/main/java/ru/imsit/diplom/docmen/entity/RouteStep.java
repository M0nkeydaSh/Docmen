package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.imsit.diplom.docmen.enums.RouteStepStatesEnum;
import ru.imsit.diplom.docmen.model.AuditEntity;

@Getter
@Setter
@Entity
@Builder
@Table(name = "route_steps")
@NoArgsConstructor
@AllArgsConstructor
public class RouteStep extends AuditEntity {

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "doc_card_id")
    private DocCard docCard;

    @Column(name = "number_of_step")
    private String numberOfStep;

    @Enumerated(EnumType.STRING)
    @Column(name = "route_step_state")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private RouteStepStatesEnum routeStepState;

}