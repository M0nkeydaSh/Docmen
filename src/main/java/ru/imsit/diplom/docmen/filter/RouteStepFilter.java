package ru.imsit.diplom.docmen.filter;

import org.springframework.data.jpa.domain.Specification;
import ru.imsit.diplom.docmen.entity.RouteStep;

import java.util.UUID;

public record RouteStepFilter(UUID id) {
    public Specification<RouteStep> toSpecification() {
        return Specification.where(idSpec());
    }

    private Specification<RouteStep> idSpec() {
        return ((root, query, cb) -> id != null
                ? cb.equal(root.get("id"), id)
                : null);
    }
}