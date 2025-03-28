package ru.imsit.diplom.docmen.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.TypeCostumer;

public record TypeCostumerFilter(String name) {
    public Specification<TypeCostumer> toSpecification() {
        return Specification.where(nameSpec());
    }

    private Specification<TypeCostumer> nameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(name)
                ? cb.equal(cb.lower(root.get("name")), name.toLowerCase())
                : null);
    }
}