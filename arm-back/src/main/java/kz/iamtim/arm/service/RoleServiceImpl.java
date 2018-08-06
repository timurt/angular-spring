package kz.iamtim.arm.service;

import kz.iamtim.arm.models.Role;
import kz.iamtim.arm.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Role service layer implementation.
 *
 * @author Timur Tibeyev.
 */
@Service
public class RoleServiceImpl implements RoleService {

    /** Role repository. */
    @Autowired
    private RoleRepository repository;

    @Override
    public Role findById(final Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Iterable<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role findByKey(final String key) {
        return repository.findFirstByKey(key);
    }
}
