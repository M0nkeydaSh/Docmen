package ru.imsit.diplom.docmen.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.security.core.userdetails.UserDetails;
import ru.imsit.diplom.docmen.model.AuditEntity;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Builder
@Table(name = "users", indexes = {
        @Index(name = "idx_securityuser_username", columnList = "USERNAME")
})
@NoArgsConstructor
@AllArgsConstructor
public class User extends AuditEntity implements UserDetails {

    @Column(name = "USERNAME", unique = true, length = 50)
    private String username;

    @Column(name = "PASSWORD", length = 500)
    private String password;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_sec_id"))
    @BatchSize(size = 50)
    private Set<Authority> authorities = new LinkedHashSet<>();

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}