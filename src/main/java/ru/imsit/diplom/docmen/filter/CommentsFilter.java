package ru.imsit.diplom.docmen.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.Comments;
import ru.imsit.diplom.docmen.entity.RouteStep;

import java.util.UUID;

public record CommentsFilter(String docCardId) {
    public Specification<Comments> toSpecification() {
        return Specification.where(docCardIdSpec());
    }

    private Specification<Comments> docCardIdSpec() {
        return ((root, query, cb) -> StringUtils.hasText(docCardId)
                ? cb.equal(root.get("docCard").get("id").as(String.class), docCardId)
                : null);
    }

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
}