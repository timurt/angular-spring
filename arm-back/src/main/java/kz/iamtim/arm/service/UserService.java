package kz.iamtim.arm.service;

import kz.iamtim.arm.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Interface for user service implementation.
 *
 * @author Timur Tibeyev.
 */
public interface UserService {
    /**
     * Saves user.
     *
     * @param user {@code User} object
     *
     * @return saved user
     */
    User save(User user);

    /**
     * Registers user. Almost the same as {@code save(User user)} method,
     * but used only for registration purpose.
     *
     * @param user {@code User} object
     *
     * @return registered user
     */
    User register(User user);

    /**
     * Returns list of users.
     *
     * @param pageable {@code Pageable} object
     *
     * @return list of users
     */
    Page<User> getByPage(Specification<User> specs, Pageable pageable);

    /**
     * Finds user by id.
     *
     * @param id user id
     *
     * @return user
     */
    User findById(Long id);

    /**
     * Checks if user with specified id exists.
     *
     * @param id user id
     *
     * @return true if exists, otherwise false
     */
    boolean userExists(Long id);

    /**
     * Deletes user with specified id.
     *
     * @param id user id
     */
    void delete(Long id);

    /**
     * Finds user by login and password.
     *
     * @param login user login
     * @param password user password
     *
     * @return user
     */
    User findByLoginAndPassword(String login, String password);

    /**
     * Checks if user with specified login exists.
     *
     * @param login user login
     *
     * @return true if exists, otherwise false
     */
    boolean existsByLogin(String login);

    /**
     * Finds user by login.
     *
     * @param login user login
     *
     * @return user
     */
    User findByLogin(String login);

    /**
     * Returns list of owners.
     *
     * @return list of owners
     */
    List<User> getOwners();
}
