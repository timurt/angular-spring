package kz.iamtim.arm.service;

import kz.iamtim.arm.models.Role;
import kz.iamtim.arm.models.User;
import kz.iamtim.arm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * User service layer implementation.
 *
 * @author Timur Tibeyev.
 */
@Service
public class UserServiceImpl implements UserService {

    /** User repository. */
    @Autowired
    UserRepository repository;

    /** Service layer for db operations over users. */
    @Autowired
    RoleService roleService;

    @Override
    @Secured("ROLE_ADMIN")
    public User save(final User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return repository.save(user);
    }

    @Override
    public User register(final User user) {
        return repository.save(user);
    }

    @Override
    public boolean existsByLogin(final String login) {
        return repository.findFirstByLogin(login) != null;
    }

    @Override
    @Secured("ROLE_ADMIN")
    public Page<User> getByPage(Specification<User> specs,
                                final Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), sort);
        return repository.findAll(specs, pageRequest);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public User findById(final Long id) {
        return repository.findById(id).get();
    }

    @Override
    @Secured("ROLE_ADMIN")
    public boolean userExists(final Long id) {
        if (id == null) {
            return false;
        }
        final User user = repository.findById(id).get();
        return !(user == null || user.isDeleted());
    }

    @Override
    @Secured("ROLE_ADMIN")
    public void delete(final Long id) {
        final User user = repository.findById(id).get();
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
        repository.save(user);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public User findByLoginAndPassword(final String login, final String password) {
        return repository.findByLoginAndPassword(login, password);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public User findByLogin(final String login) {
        return repository.findFirstByLogin(login);
    }

    @Override
    @Secured("ROLE_ADMIN")
    public List<User> getOwners() {
        Role role = roleService.findByKey("OWNER");
        return repository.findByRoleAndIsDeletedFalse(role);
    }


}
