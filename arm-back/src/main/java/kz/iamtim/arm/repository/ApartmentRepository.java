package kz.iamtim.arm.repository;

import kz.iamtim.arm.models.Apartment;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository interface for {@code Apartment} class.
 *
 * @author Timur Tibeyev.
 */
public interface ApartmentRepository extends PagingAndSortingRepository<Apartment, Long>,
        JpaSpecificationExecutor<Apartment> {
}
