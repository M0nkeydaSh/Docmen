package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@Table(name = "type_costumer")
@NoArgsConstructor
@AllArgsConstructor
public class TypeCostumer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departments_id")
    private Departments departments;

    @Column(name = "change_date")
    private String changeDate;

}