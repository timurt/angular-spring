package kz.iamtim.arm.specs;

import kz.iamtim.arm.models.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

/**
 * Restaurant specification.
 *
 * @author Timur Tibeyev.
 */
public class RestaurantSpecification {

    /**
     * Filters restaurants on given inputs.
     *
     * @param averageCheckFrom lower bound for {@code Restaurant.averageCheck}
     * @param averageCheckTo   upper bound for {@code Restaurant.averageCheck}
     * @param numberOfSeatsFrom lower bound for {@code Restaurant.numberOfSeats}
     * @param numberOfSeatsTo   upper bound for {@code Restaurant.numberOfSeats}
     * @param role current user role
     *
     * @return specification object
     */
    public static Specification<Restaurant> filtered(final Double averageCheckFrom,
                                                     final Double averageCheckTo,
                                                     final Integer numberOfSeatsFrom,
                                                     final Integer numberOfSeatsTo,
                                                     final String role,
                                                     final Long userId,
                                                     final Boolean hasWifi
    ) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate averageCheckFromPredicate = criteriaBuilder.and();
            if (averageCheckFrom != null) {
                averageCheckFromPredicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.get("averageCheck"), averageCheckFrom);
            }

            if (averageCheckTo != null) {
                averageCheckFromPredicate = criteriaBuilder.and(
                        averageCheckFromPredicate,
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("averageCheck"), averageCheckTo)
                );
            }

            Predicate numberOfSeatsPredicate = criteriaBuilder.and();
            if (numberOfSeatsFrom != null) {
                numberOfSeatsPredicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.get("numberOfSeats"), numberOfSeatsFrom);
            }

            if (numberOfSeatsTo != null) {
                numberOfSeatsPredicate = criteriaBuilder.and(
                        numberOfSeatsPredicate,
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("numberOfSeats"), numberOfSeatsTo)
                );
            }

            Predicate isDeletedPredicate = criteriaBuilder.equal(
                    root.get("isDeleted"), false);

            Predicate hasWifiPredicate = criteriaBuilder.and();
            if (hasWifi != null) {
                hasWifiPredicate = criteriaBuilder.equal(
                        root.get("hasWifi"), hasWifi);
            }

            Predicate ownerPredicate = criteriaBuilder.and();
            if ("OWNER".equals(role)) {
                ownerPredicate = criteriaBuilder.equal(
                        root.get("owner").get("id"), userId);
            }

            return criteriaBuilder.and(averageCheckFromPredicate,
                    numberOfSeatsPredicate, hasWifiPredicate,
                    isDeletedPredicate, ownerPredicate);
        };
    }
}
