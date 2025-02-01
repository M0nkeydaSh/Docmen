package ru.imsit.diplom.docmen.filtr;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.Files;

public record FilesFilter(String name) {
    public Specification<Files> toSpecification() {
        return Specification.where(nameSpec());
    }

    private Specification<Files> nameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(name)
                ? cb.equal(root.get("name"), name)
                : null);
    }
}