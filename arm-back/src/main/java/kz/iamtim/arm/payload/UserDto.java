package kz.iamtim.arm.payload;

import kz.iamtim.arm.models.User;

import javax.validation.constraints.NotBlank;

/**
 * Dto class for representing {@code User} class.
 *
 * @author Timur Tibeyev.
 */
public class UserDto {
    /** Id. */
    private Long id;

    /** Name. */
    @NotBlank
    private String name;

    /** Login. */
    @NotBlank
    private String login;

    /** Role type. */
    @NotBlank
    private String roleType;

    /**
     * Creates new instance of {@code UserDto} class.
     *
     * @param user user
     */
    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.roleType = user.getRole().getKey();
    }

    /**
     * Creates new instance of {@code ErrorResponse} class.
     */
    public UserDto() {

    }

    /**
     * Getter method.
     *
     * @return entity id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method.
     *
     * @param value entity id
     */
    public void setId(Long value) {
        this.id = value;
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
}
