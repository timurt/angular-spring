package kz.iamtim.arm.security;

import kz.iamtim.arm.models.User;
import kz.iamtim.arm.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@code UserDetailsService} implementation.
 *
 * @author Timur Tibeyev.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    /** User repository. */
    @Autowired
    private UserRepository userRepository;

    /** Logger. */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userRepository.findFirstByLogin(username);

        if (user == null || user.isDeleted()) {
            LOGGER.error("User not found with username {}", username);
            throw new UsernameNotFoundException(username);
        }

        return new MyUserPrincipal(user);
    }

    /**
     * Loads user by id.
     *
     * @param id user id
     *
     * @return user details
     */
    @Transactional
    public UserDetails loadUserById(final Long id) {
        User user = userRepository.findById(id).get();

        if (user == null) {
            LOGGER.error("User not found with id {}", id);
            throw new UsernameNotFoundException("User not found with id : " + id);
        }

        return new MyUserPrincipal(user);
    }
}
