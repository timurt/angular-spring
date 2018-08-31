package kz.iamtim.arm.service;

import kz.iamtim.arm.models.Restaurant;
import kz.iamtim.arm.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Restaurant service layer implementation.
 *
 * @author Timur Tibeyev.
 */
@Service
public class RestaurantServiceImpl implements RestaurantService {

    /** Restaurant repository. */
    @Autowired
    RestaurantRepository repository;

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    public Restaurant save(final Restaurant restaurant) {
        restaurant.setUpdatedAt(LocalDateTime.now());

        return repository.save(restaurant);
    }

    @Override
    public Page<Restaurant> getByPage(final Specification<Restaurant> specs,
                                      final Pageable pageable,
                                      final String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        if (sortBy != null) {
            switch (sortBy) {
                case "priceAsc" :
                    sort = Sort.by(Sort.Direction.ASC, "pricePerMonth");
                    break;
                case "priceDesc" :
                    sort = Sort.by(Sort.Direction.DESC, "pricePerMonth");
                    break;
                case "dateAsc" :
                    sort = Sort.by(Sort.Direction.ASC, "createdAt");
                    break;
                case "dateDesc" :
                    sort = Sort.by(Sort.Direction.DESC, "createdAt");
                    break;
            }
        }
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), sort);
        return repository.findAll(specs, pageRequest);
    }

    @Override
    public Restaurant findById(final Long id) {
        return repository.findById(id).get();
    }

    @Override
    public boolean restaurantExists(final Long id) {
        if (id == null) {
            return false;
        }
        Optional<Restaurant> optional = repository.findById(id);
        return optional.isPresent() && !optional.get().isDeleted();
    }

    @Override
    @Secured({"ROLE_ADMIN", "ROLE_OWNER"})
    public void delete(final Long id) {
        final Restaurant restaurant = repository.findById(id).get();
        restaurant.setDeleted(true);
        restaurant.setDeletedAt(LocalDateTime.now());
        repository.save(restaurant);
    }
}
