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
@Table(name = "doc_card")
public class DocCard {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @Column(name = "discription")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String discription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type_document")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String typeDocument;

    @Column(name = "reg_num")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String regNum;

    @Column(name = "key_words")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String keyWords;

    @Column(name = "change_date")
    private String changeDate;





}