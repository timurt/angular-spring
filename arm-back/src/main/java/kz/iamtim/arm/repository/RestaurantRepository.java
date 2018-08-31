package kz.iamtim.arm.repository;

import kz.iamtim.arm.models.Restaurant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository interface for {@code Restaurant} class.
 *
 * @author Timur Tibeyev.
 */
public interface RestaurantRepository extends PagingAndSortingRepository<Restaurant, Long>,
        JpaSpecificationExecutor<Restaurant> {
}
