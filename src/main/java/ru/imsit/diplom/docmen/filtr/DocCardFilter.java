package ru.imsit.diplom.docmen.filtr;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.DocCard;

public record DocCardFilter(String name, String typeDocument, String keyWords) {
    public Specification<DocCard> toSpecification() {
        return Specification.where(nameSpec())
                .and(typeDocumentSpec())
                .and(keyWordsSpec());
    }

    private Specification<DocCard> nameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(name)
                ? cb.equal(root.get("name"), name)
                : null);
    }

    private Specification<DocCard> typeDocumentSpec() {
        return ((root, query, cb) -> StringUtils.hasText(typeDocument)
                ? cb.equal(root.get("typeDocument"), typeDocument)
                : null);
    }

    private Specification<DocCard> keyWordsSpec() {
        return ((root, query, cb) -> StringUtils.hasText(keyWords)
                ? cb.equal(root.get("keyWords"), keyWords)
                : null);
    }
}