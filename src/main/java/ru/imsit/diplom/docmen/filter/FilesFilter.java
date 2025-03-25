package ru.imsit.diplom.docmen.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.Files;

public record FilesFilter(String docCardId) {
    public Specification<Files> toSpecification() {
        return Specification.where(docCardIdSpec());
    }

    private Specification<Files> docCardIdSpec() {
        return ((root, query, cb) -> StringUtils.hasText(docCardId)
                ? cb.equal(root.get("docCard").get("id").as(String.class), docCardId)
                : null);
    }
}