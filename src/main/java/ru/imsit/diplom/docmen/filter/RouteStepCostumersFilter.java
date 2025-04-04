package ru.imsit.diplom.docmen.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.imsit.diplom.docmen.entity.Costumers;
import ru.imsit.diplom.docmen.entity.RouteStepCostumers;

public record RouteStepCostumersFilter(Costumers costumers) {
    public Specification<RouteStepCostumers> toSpecification() {
        return Specification.where(costumersSpec());
    }

    private Specification<RouteStepCostumers> costumersSpec() {
        return ((root, query, cb) -> costumers != null
                ? cb.equal(root.get("costumers"), costumers)
                : null);
    }
}