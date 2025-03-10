package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.TypeCostumer;

import java.util.Optional;
import java.util.UUID;

public interface TypeCostumerRepository extends JpaRepository<TypeCostumer, UUID>, JpaSpecificationExecutor<TypeCostumer> {
    Optional<TypeCostumer> findByName(String name);
}