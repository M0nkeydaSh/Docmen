package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.imsit.diplom.docmen.model.AuditEntity;

@Getter
@Setter
@Entity
@Builder
@Table(name = "files")
@NoArgsConstructor
@AllArgsConstructor
public class Files extends AuditEntity {

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_card_id")
    private DocCard docCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}