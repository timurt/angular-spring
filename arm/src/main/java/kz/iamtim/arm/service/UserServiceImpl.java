package kz.iamtim.arm.service;

import kz.iamtim.arm.models.User;
import kz.iamtim.arm.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Documentation for {@code UserServiceImpl}.
 *
 * @author Timur Tibeyev.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    @Override
    public Page<User> getByPage(Pageable pageable) {
        return repository.findByIsDeletedIsFalse(pageable);
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public boolean userExists(Long id) {
        if (id == null) {
            return false;
        }
        final User user = repository.findById(id).get();
        return !(user == null || user.isDeleted());
    }

    @Override
    public void delete(Long id) {
        final User user = repository.findById(id).get();
        user.setDeleted(true);
        repository.save(user);
    }
}
