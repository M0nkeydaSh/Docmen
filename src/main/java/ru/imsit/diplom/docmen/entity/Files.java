package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "files")
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doc_card_id")
    private DocCard docCard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "change_date")
    private String changeDate;
}