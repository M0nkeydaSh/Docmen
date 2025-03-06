package ru.imsit.diplom.docmen.filtr;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.Departments;

public record DepartmentsFilter(String name) {
    public Specification<Departments> toSpecification() {
        return Specification.where(nameSpec());
    }

    private Specification<Departments> nameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(name)
                ? cb.equal(root.get("name"), name)
                : null);
    }
}