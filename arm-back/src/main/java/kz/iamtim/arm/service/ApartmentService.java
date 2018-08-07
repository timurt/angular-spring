package kz.iamtim.arm.service;

import kz.iamtim.arm.models.Apartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Interface for apartment service implementation.
 *
 * @author Timur Tibeyev.
 */
public interface ApartmentService {
    /**
     * Saves apartment.
     *
     * @param apartment apartment
     *
     * @return apartment
     */
    Apartment save(Apartment apartment);

    /**
     * Returns list of apartments.
     *
     * @param specs apartment specification
     * @param pageable {@code Pageable} object
     *
     * @return list of apartments
     */
    Page<Apartment> getByPage(Specification<Apartment> specs, Pageable pageable, String sortBy);

    /**
     * Finds apartment by id.
     *
     * @param id apartment id
     *
     * @return apartment
     */
    Apartment findById(Long id);

    /**
     * Checks if apartment with specified id exists.
     *
     * @param id apartment id
     *
     * @return true if exists, otherwise false
     */
    boolean apartmentExists(Long id);

    /**
     * Deletes apartment with specified id.
     *
     * @param id apartment id
     */
    void delete(Long id);

    /**
     * Sets specified apartment to available/rented state.
     *
     * @param id apartment id
     * @param rented boolean value
     */
    void setRented(Long id, boolean rented);
}
