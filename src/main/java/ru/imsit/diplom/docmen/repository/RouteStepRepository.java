package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.RouteStep;

import java.util.UUID;

public interface RouteStepRepository extends JpaRepository<RouteStep, UUID>, JpaSpecificationExecutor<RouteStep> {
}