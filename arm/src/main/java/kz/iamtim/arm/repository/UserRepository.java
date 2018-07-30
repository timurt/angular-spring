package kz.iamtim.arm.repository;

import kz.iamtim.arm.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Documentation for {@code UserRepository}.
 *
 * @author Timur Tibeyev.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Page<User> findByIsDeletedIsFalse(Pageable var1);

}
