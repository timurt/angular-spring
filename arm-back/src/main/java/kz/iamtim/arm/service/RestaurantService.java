package kz.iamtim.arm.service;

import kz.iamtim.arm.models.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Interface for restaurant service implementation.
 *
 * @author Timur Tibeyev.
 */
public interface RestaurantService {
    /**
     * Saves restaurant.
     *
     * @param restaurant restaurant
     *
     * @return restaurant
     */
    Restaurant save(Restaurant restaurant);

    /**
     * Returns list of restaurants.
     *
     * @param specs restaurant specification
     * @param pageable {@code Pageable} object
     *
     * @return list of restaurants
     */
    Page<Restaurant> getByPage(Specification<Restaurant> specs, Pageable pageable, String sortBy);

    /**
     * Finds restaurant by id.
     *
     * @param id restaurant id
     *
     * @return restaurant
     */
    Restaurant findById(Long id);

    /**
     * Checks if restaurant with specified id exists.
     *
     * @param id restaurant id
     *
     * @return true if exists, otherwise false
     */
    boolean restaurantExists(Long id);

    /**
     * Deletes restaurant with specified id.
     *
     * @param id restaurant id
     */
    void delete(Long id);
}
