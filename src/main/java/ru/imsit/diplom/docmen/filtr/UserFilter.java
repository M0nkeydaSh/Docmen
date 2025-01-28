package ru.imsit.diplom.docmen.filtr;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.User;

public record UserFilter(String login) {
    public Specification<User> toSpecification() {
        return Specification.where(loginSpec());
    }

    private Specification<User> loginSpec() {
        return ((root, query, cb) -> StringUtils.hasText(login)
                ? cb.equal(root.get("login"), login)
                : null);
    }
}