package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.imsit.diplom.docmen.enums.RouteStepStatesEnum;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "route_steps")
@NoArgsConstructor
@AllArgsConstructor
public class RouteStep {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_card_id")
    private DocCard docCard;

    @Column(name = "number_of_step")
    private String numberOfStep;

    @Enumerated(EnumType.STRING)
    @Column(name = "route_step_state")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private RouteStepStatesEnum routeStepState;

    @Column(name = "change_date")
    private String changeDate;

}