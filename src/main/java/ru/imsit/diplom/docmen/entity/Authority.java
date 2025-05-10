package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import ru.imsit.diplom.docmen.model.BaseEntity;

@Getter
@Setter
@Entity
@Builder
@Table(name = "authority")
@NoArgsConstructor
@AllArgsConstructor
public class Authority extends BaseEntity implements GrantedAuthority {
    
    @Column(name = "AUTHORITY", unique = true, length = 50)
    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}