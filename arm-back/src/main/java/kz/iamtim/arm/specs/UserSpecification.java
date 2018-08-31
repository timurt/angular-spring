package kz.iamtim.arm.specs;

import kz.iamtim.arm.models.Role;
import kz.iamtim.arm.models.User;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

/**
 * Restaurant specification.
 *
 * @author Timur Tibeyev.
 */
public class UserSpecification {

    /**
     * Filters users on given inputs.
     *
     * @param search search text
     * @param role user role
     *
     * @return specification object
     */
    public static Specification<User> filtered(final String search,
                                               final Role role) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate searchPredicate = criteriaBuilder.and();
            if (search != null && !search.isEmpty()) {
                String searchText = "%" + search.toLowerCase() + "%";
                searchPredicate = criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                                searchText),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("login")),
                                searchText)
                );
            }

            Predicate roleTypePredicate = criteriaBuilder.and();
            if (role != null) {
                roleTypePredicate =
                        criteriaBuilder.equal(root.get("role"), role);
            }

            Predicate isDeletedPredicate = criteriaBuilder.equal(
                    root.get("isDeleted"), false);
            return criteriaBuilder.and(searchPredicate, roleTypePredicate, isDeletedPredicate);
        };
    }
}
