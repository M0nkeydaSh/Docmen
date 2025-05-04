package ru.imsit.diplom.docmen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.imsit.diplom.docmen.entity.DocCardRoute;

import java.util.UUID;

public interface DocCardRouteRepository extends JpaRepository<DocCardRoute, UUID>, JpaSpecificationExecutor<DocCardRoute> {
    @Modifying(flushAutomatically = true)
    @Query("DELETE FROM DocCardRoute dcr WHERE dcr.routeStep.id = :routeStepId")
    void deleteAllByRouteStep_Id(UUID routeStepId);

}