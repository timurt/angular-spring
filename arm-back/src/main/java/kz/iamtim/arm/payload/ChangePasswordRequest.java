package kz.iamtim.arm.payload;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Rest request class containing id and password for changing password.
 *
 * @author Timur Tibeyev.
 */
public class ChangePasswordRequest {
    /** Id. */
    @NotNull
    private Long id;

    /** Password. */
    @NotBlank
    private String password;

    /**
     * Getter method.
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter method.
     *
     * @param value id
     */
    public void setId(Long value) {
        this.id = value;
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
