package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.RouteStepCostumers;

import java.util.UUID;

public interface RouteStepCostumersRepository extends JpaRepository<RouteStepCostumers, UUID>, JpaSpecificationExecutor<RouteStepCostumers> {
}