package kz.iamtim.arm.payload;

/**
 * Rest response class for cases when error occurs.
 *
 * @author Timur Tibeyev.
 */
public class ErrorResponse {
    /** Message. */
    private String message;

    /**
     * Creates new instance of {@code ErrorResponse} class.
     *
     * @param mes error message
     */
    public ErrorResponse(String mes) {
        this.message = mes;
    }

    /**
     * Getter method.
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter method.
     *
     * @param value message
     */
    public void setMessage(String value) {
        this.message = value;
    }
}
