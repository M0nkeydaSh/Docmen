package ru.imsit.diplom.docmen.filter;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import ru.imsit.diplom.docmen.entity.Costumers;

public record CostumersFilter(String firstName, String lastName, String surName, String gender) {
    public Specification<Costumers> toSpecification() {
        return Specification.where(firstNameSpec())
                .and(lastNameSpec())
                .and(surNameSpec())
                .and(genderSpec());
    }

    private Specification<Costumers> firstNameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(firstName)
                ? cb.equal(cb.lower(root.get("firstName")), firstName.toLowerCase())
                : null);
    }

    private Specification<Costumers> lastNameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(lastName)
                ? cb.equal(cb.lower(root.get("lastName")), lastName.toLowerCase())
                : null);
    }

    private Specification<Costumers> surNameSpec() {
        return ((root, query, cb) -> StringUtils.hasText(surName)
                ? cb.equal(cb.lower(root.get("surName")), surName.toLowerCase())
                : null);
    }

    private Specification<Costumers> genderSpec() {
        return ((root, query, cb) -> StringUtils.hasText(gender)
                ? cb.equal(cb.lower(root.get("gender")), gender.toLowerCase())
                : null);
    }
}