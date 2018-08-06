package kz.iamtim.arm.service;

import kz.iamtim.arm.models.Role;

/**
 * Interface for role service implementation.
 *
 * @author Timur Tibeyev.
 */
public interface RoleService {
    /**
     * Finds role by id.
     *
     * @param id role id
     *
     * @return role
     */
    Role findById(Long id);

    /**
     * Returns list of roles.
     *
     * @return list of roles
     */
    Iterable<Role> findAll();

    /**
     * Finds role by key.
     *
     * @param key role key
     *
     * @return role
     */
    Role findByKey(String key);
}
