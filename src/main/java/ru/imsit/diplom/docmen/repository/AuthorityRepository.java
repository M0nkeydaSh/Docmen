package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.Authority;

import java.util.UUID;

public interface AuthorityRepository extends JpaRepository<Authority, UUID>, JpaSpecificationExecutor<Authority> {

    Authority findByName(String authority );
}
