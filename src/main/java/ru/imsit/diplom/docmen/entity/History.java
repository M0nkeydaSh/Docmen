package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.imsit.diplom.docmen.enums.StatesEnum;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "history")
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_card_id")
    private DocCard docCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private StatesEnum state;

    @Column(name = "change_date")
    private String changeDate;

}