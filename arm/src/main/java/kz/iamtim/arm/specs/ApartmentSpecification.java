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
     * @return specification object
     */
    public static Specification<Apartment> filtered(Double floorAreaSizeFrom,
                                                    Double floorAreaSizeTo,
                                                    Double pricePerMonthFrom,
                                                    Double pricePerMonthTo,
                                                    Integer numberOfRoomsFrom,
                                                    Integer numberOfRoomsTo) {
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

            return criteriaBuilder.and(floorAreaSizePredicate,
                    pricePerMonthPredicate, numberOfRoomsPredicate, isDeletedPredicate);
        };
    }
}
