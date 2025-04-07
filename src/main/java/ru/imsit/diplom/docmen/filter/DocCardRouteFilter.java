package ru.imsit.diplom.docmen.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.imsit.diplom.docmen.entity.DocCard;
import ru.imsit.diplom.docmen.entity.DocCardRoute;
import ru.imsit.diplom.docmen.entity.RouteStepCostumers;

public record DocCardRouteFilter(RouteStepCostumers routeStepCostumers, DocCard routeStepCostumersRouteStepDocCard) {
    public Specification<DocCardRoute> toSpecification() {
        return Specification.where(routeStepCostumersSpec())
                .and(routeStepCostumersRouteStepDocCardSpec());
    }

    private Specification<DocCardRoute> routeStepCostumersSpec() {
        return ((root, query, cb) -> routeStepCostumers != null
                ? cb.equal(root.get("routeStepCostumers"), routeStepCostumers)
                : null);
    }

    private Specification<DocCardRoute> routeStepCostumersRouteStepDocCardSpec() {
        return ((root, query, cb) -> routeStepCostumersRouteStepDocCard != null
                ? cb.equal(root.get("routeStepCostumers").get("routeStep").get("docCard"), routeStepCostumersRouteStepDocCard)
                : null);
    }
}