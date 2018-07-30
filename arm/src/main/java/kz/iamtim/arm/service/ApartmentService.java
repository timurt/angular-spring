package kz.iamtim.arm.service;

import kz.iamtim.arm.models.Apartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Documentation for {@code ApartmentService}.
 *
 * @author Timur Tibeyev.
 */
public interface ApartmentService {
    Apartment save(Apartment apartment);

    Page<Apartment> getByPage(Specification<Apartment> specs, Pageable pageable);

    Apartment findById(Long id);

    boolean apartmentExists(Long id);

    void delete(Long id);
}
