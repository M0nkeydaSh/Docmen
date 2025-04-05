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
@Table(name = "doc_card")
@NoArgsConstructor
@AllArgsConstructor
public class DocCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @Column(name = "description")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_document_id")
    private TypeDocument typeDocument;

    @Column(name = "reg_num")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String regNum;

    @Column(name = "key_words")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String keyWords;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private StatesEnum state;

    @Column(name = "change_date")
    private String changeDate;





}