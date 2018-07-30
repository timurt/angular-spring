package kz.iamtim.arm.service;

import kz.iamtim.arm.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Documentation for {@code UserService}.
 *
 * @author Timur Tibeyev.
 */
public interface UserService {
    User save(User user);

    Page<User> getByPage(Pageable pageable);

    User findById(Long id);

    boolean userExists(Long id);

    void delete(Long id);
}
