package ru.imsit.diplom.docmen.filter;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.History;

public record HistoryFilter(@NotNull String docCardId) {
    public Specification<History> toSpecification() {
        return Specification.where(docCardIdSpec());
    }

    private Specification<History> docCardIdSpec() {
        return ((root, query, cb) -> StringUtils.hasText(docCardId)
                ? cb.equal(root.get("docCard").get("id").as(String.class), docCardId)
                : null);
    }
}