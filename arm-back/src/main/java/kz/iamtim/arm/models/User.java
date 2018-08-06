package kz.iamtim.arm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * User model.
 *
 * @author Timur Tibeyev.
 */
@Entity
@Table(name = "users")
public class User extends PersistenceObject {

    /** User name. */
    @Column(name = "name")
    private String name;

    /** User login. */
    @Column(name = "login", unique = true)
    private String login;

    /** User password. */
    @Column(name = "password")
    private String password;

    /** User role. */
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    /**
     * Getter method.
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method.
     *
     * @param value name
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Getter method.
     *
     * @return login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Setter method.
     *
     * @param value login
     */
    public void setLogin(String value) {
        this.login = value;
    }

    /**
     * Getter method.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method.
     *
     * @param value password
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Getter method.
     *
     * @return role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Setter method.
     *
     * @param value role
     */
    public void setRole(Role value) {
        this.role = value;
    }
}
