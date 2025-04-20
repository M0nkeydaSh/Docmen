package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.imsit.diplom.docmen.entity.DocCardRoute;

import java.util.UUID;

public interface DocCardRouteRepository extends JpaRepository<DocCardRoute, UUID>, JpaSpecificationExecutor<DocCardRoute> {
    void deleteAllByRouteStep_Id(UUID routeStepId);

}