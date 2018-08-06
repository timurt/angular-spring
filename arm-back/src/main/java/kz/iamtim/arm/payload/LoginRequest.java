package kz.iamtim.arm.payload;

import javax.validation.constraints.NotBlank;

/**
 * Rest request class containing login and password for authentication.
 *
 * @author Timur Tibeyev.
 */
public class LoginRequest {
    /** Login. */
    @NotBlank
    private String login;

    /** Password. */
    @NotBlank
    private String password;

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
}
