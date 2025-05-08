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
@Table(name = "type_costumer")
@NoArgsConstructor
@AllArgsConstructor
public class TypeCostumer extends AuditEntity {

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "departments_id")
    private Departments department;

}