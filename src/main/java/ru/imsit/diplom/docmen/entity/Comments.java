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
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
public class Comments extends AuditEntity {

    @Column(name = "content")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_card_id")
    private DocCard docCard;

}