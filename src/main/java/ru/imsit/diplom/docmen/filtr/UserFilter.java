package ru.imsit.diplom.docmen.filtr;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.User;

public record UserFilter(String username) {
    public Specification<User> toSpecification() {
        return Specification.where(usernameSpec());
    }

    private Specification<User> usernameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(username)
                ? cb.equal(root.get("username"), username)
                : null);
    }
}