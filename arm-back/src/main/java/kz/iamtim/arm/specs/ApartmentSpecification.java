package kz.iamtim.arm.specs;

import kz.iamtim.arm.models.Apartment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;

/**
 * Apartment specification.
 *
 * @author Timur Tibeyev.
 */
public class ApartmentSpecification {

    /**
     * Filters apartments on given inputs.
     *
     * @param floorAreaSizeFrom lower bound for {@code Apartment.floorAreaSize}
     * @param floorAreaSizeTo   upper bound for {@code Apartment.floorAreaSize}
     * @param pricePerMonthFrom lower bound for {@code Apartment.pricePerMonth}
     * @param pricePerMonthTo   upper bound for {@code Apartment.pricePerMonth}
     * @param numberOfRoomsFrom lower bound for {@code Apartment.numberOfRooms}
     * @param numberOfRoomsTo   upper bound for {@code Apartment.numberOfRooms}
     * @param role current user role
     * @param isRented boolean property {@code Apartment.isRented}
     *
     * @return specification object
     */
    public static Specification<Apartment> filtered(final Double floorAreaSizeFrom,
                                                    final Double floorAreaSizeTo,
                                                    final Double pricePerMonthFrom,
                                                    final Double pricePerMonthTo,
                                                    final Integer numberOfRoomsFrom,
                                                    final Integer numberOfRoomsTo,
                                                    final String role,
                                                    final Boolean isRented) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            Predicate floorAreaSizePredicate = criteriaBuilder.and();
            if (floorAreaSizeFrom != null) {
                floorAreaSizePredicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.get("floorAreaSize"), floorAreaSizeFrom);
            }

            if (floorAreaSizeTo != null) {
                floorAreaSizePredicate = criteriaBuilder.and(
                        floorAreaSizePredicate,
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("floorAreaSize"), floorAreaSizeTo)
                );
            }

            Predicate pricePerMonthPredicate = criteriaBuilder.and();
            if (pricePerMonthFrom != null) {
                pricePerMonthPredicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.get("pricePerMonth"), pricePerMonthFrom);
            }

            if (pricePerMonthTo != null) {
                pricePerMonthPredicate = criteriaBuilder.and(
                        pricePerMonthPredicate,
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("pricePerMonth"), pricePerMonthTo)
                );
            }

            Predicate numberOfRoomsPredicate = criteriaBuilder.and();
            if (numberOfRoomsFrom != null) {
                numberOfRoomsPredicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.get("numberOfRooms"), numberOfRoomsFrom);
            }

            if (numberOfRoomsTo != null) {
                numberOfRoomsPredicate = criteriaBuilder.and(
                        numberOfRoomsPredicate,
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("numberOfRooms"), numberOfRoomsTo)
                );
            }

            Predicate isDeletedPredicate = criteriaBuilder.equal(
                    root.get("isDeleted"), false);

            Predicate isRentedPredicate = criteriaBuilder.and();
            if ("CLIENT".equals(role)) {
                isRentedPredicate = criteriaBuilder.equal(
                        root.get("isRented"), false);
            } else {
                if (isRented != null) {
                    isRentedPredicate = criteriaBuilder.equal(
                            root.get("isRented"), isRented);
                }
            }

            return criteriaBuilder.and(floorAreaSizePredicate,
                    pricePerMonthPredicate, numberOfRoomsPredicate,
                    isDeletedPredicate, isRentedPredicate);
        };
    }
}
