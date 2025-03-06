package ru.imsit.diplom.docmen.filtr;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.Comments;

public record CommentsFilter(String changeDate) {
    public Specification<Comments> toSpecification() {
        return Specification.where(changeDateSpec());
    }

    private Specification<Comments> changeDateSpec() {
        return ((root, query, cb) -> StringUtils.hasText(changeDate)
                ? cb.equal(root.get("changeDate"), changeDate)
                : null);
    }
}