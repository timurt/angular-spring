package kz.iamtim.arm.payload;

import javax.validation.constraints.NotBlank;

/**
 * Rest request class containing login and password for authentication.
 *
 * @author Timur Tibeyev.
 */
public class RegisterRequest {
    /** User login. */
    @NotBlank
    private String login;

    /** User name. */
    @NotBlank
    private String name;

    /** User password. */
    @NotBlank
    private String password;

    /** User role type. */
    @NotBlank
    private String roleType;

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
     * @return role type
     */
    public String getRoleType() {
        return roleType;
    }

    /**
     * Setter method.
     *
     * @param value role type
     */
    public void setRoleType(String value) {
        this.roleType = value;
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
}
