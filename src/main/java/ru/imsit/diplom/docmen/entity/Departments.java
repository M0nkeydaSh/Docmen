package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import ru.imsit.diplom.docmen.model.AuditEntity;

@Getter
@Setter
@Entity
@Builder
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
public class Departments extends AuditEntity {

    @Column(name = "name")
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private String name;

}