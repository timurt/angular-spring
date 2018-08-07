package kz.iamtim.arm.repository;

import kz.iamtim.arm.models.Role;
import kz.iamtim.arm.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * Repository interface for {@code User} class.
 *
 * @author Timur Tibeyev.
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long>,
        JpaSpecificationExecutor<User> {
    /**
     * Returns list of users.
     *
     * @param var1 the {@code Pageable} object
     *
     * @return list of users
     */
    Page<User> findByIsDeletedIsFalseOrderByUpdatedAtDesc(Pageable var1);

    /**
     * Finds user by login and password.
     *
     * @param login the login
     * @param password the password
     *
     * @return user
     */
    User findByLoginAndPassword(String login, String password);

    /**
     * Finds user by login.
     *
     * @param login the login
     *
     * @return user
     */
    User findFirstByLogin(String login);

    /**
     * Finds users by role.
     *
     * @param role the role
     *
     * @return list of users
     */
    List<User> findByRoleAndIsDeletedFalse(Role role);

}
