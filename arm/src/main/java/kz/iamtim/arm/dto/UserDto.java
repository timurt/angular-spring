package kz.iamtim.arm.dto;

import kz.iamtim.arm.models.User;

/**
 * Documentation for {@code UserDto}.
 *
 * @author Timur Tibeyev.
 */
public class UserDto {

    private Long id;

    private String name;

    private String login;

    private String roleType;

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.login = user.getLogin();
        this.roleType = user.getRoleType().name();
    }

    public UserDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }
}
