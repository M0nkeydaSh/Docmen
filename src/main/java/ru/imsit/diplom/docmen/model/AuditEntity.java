package ru.imsit.diplom.docmen.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
public class AuditEntity extends BaseEntity{
    @Column(name = "change_date")
    private LocalDateTime changeDate = LocalDateTime.now();

}
