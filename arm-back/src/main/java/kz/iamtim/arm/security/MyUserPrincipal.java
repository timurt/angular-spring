package kz.iamtim.arm.security;

import kz.iamtim.arm.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * User principal.
 *
 * @author Timur Tibeyev.
 */
public class MyUserPrincipal implements UserDetails {

    /** User. */
    private User user;

    /** Authorities. */
    private List<GrantedAuthority> authorities;

    /** Role prefix. */
    private static final String ROLE_PREFIX = "ROLE_";

    /**
     * Creates new instance of {@code MyUserPrincipal} object.
     *
     * @param authUser authenticated user
     */
    public MyUserPrincipal(User authUser) {
        this.user = authUser;
        authorities = Collections.singletonList(
                new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().getKey()));
    }

    /**
     * Returns user.
     *
     * @return user
     */
    public User getUser() {
        return this.user;
    }

    /**
     * Returns user id.
     *
     * @return user id
     */
    public Long getId() {
        return user.getId();
    }

    /**
     * Returns user name.
     *
     * @return user name
     */
    public String getName() {
        return user.getName();
    }

    /**
     * Returns user role.
     *
     * @return user role
     */
    public String getRole() {
        return user.getRole().getKey();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
