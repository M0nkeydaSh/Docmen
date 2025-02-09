package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.Costumers;

import java.util.UUID;

public interface CostumersRepository extends JpaRepository<Costumers, UUID>, JpaSpecificationExecutor<Costumers> {
}