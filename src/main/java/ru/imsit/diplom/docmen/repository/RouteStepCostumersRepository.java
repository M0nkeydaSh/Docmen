package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.RouteStepCostumers;

import java.util.List;
import java.util.UUID;

public interface RouteStepCostumersRepository extends JpaRepository<RouteStepCostumers, UUID>, JpaSpecificationExecutor<RouteStepCostumers> {
    List<RouteStepCostumers> findAllByRouteStepId(UUID routeStepId);
}