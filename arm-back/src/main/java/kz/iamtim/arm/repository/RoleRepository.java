package kz.iamtim.arm.repository;

import kz.iamtim.arm.models.Role;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository interface for {@code Role} class.
 *
 * @author Timur Tibeyev.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    /**
     * Returns role by key.
     *
     * @param key the role key
     *
     * @return role
     */
    Role findFirstByKey(String key);
}
