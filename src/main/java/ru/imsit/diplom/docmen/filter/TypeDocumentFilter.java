package ru.imsit.diplom.docmen.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.TypeDocument;

public record TypeDocumentFilter(String name) {
    public Specification<TypeDocument> toSpecification() {
        return Specification.where(nameSpec());
    }

    private Specification<TypeDocument> nameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(name)
                ? cb.equal(cb.lower(root.get("name")), name.toLowerCase())
                : null);
    }
}