package kz.iamtim.arm.payload;

/**
 * Rest response class containing JWT token and authorization information.
 *
 * @author Timur Tibeyev.
 */
public class JwtAuthenticationResponse {
    /** Access token. */
    private String accessToken;

    /** Token type. */
    private String tokenType = "Bearer";

    /** Expiration time. */
    private Long expirationTime;

    /** Login. */
    private String login;

    /** Tole. */
    private String role;

    /**
     * Getter method.
     *
     * @return expiration time
     */
    public Long getExpirationTime() {
        return expirationTime;
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
     * Setter method.
     *
     * @param value expiration time
     */
    public void setExpirationTime(Long value) {
        this.expirationTime = value;
    }

    /**
     * Creates new instance of {@code JwtAuthenticationResponse} class.
     *
     * @param accessTokenVal access token
     * @param expirationTimeVal expiration time
     */
    public JwtAuthenticationResponse(String accessTokenVal, Long expirationTimeVal) {
        this.accessToken = accessTokenVal;
        this.expirationTime = expirationTimeVal;
    }

    /**
     * Getter method.
     *
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * Setter method.
     *
     * @param value role
     */
    public void setRole(String value) {
        this.role = value;
    }

    /**
     * Getter method.
     *
     * @return access token
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * Setter method.
     *
     * @param value access token
     */
    public void setAccessToken(String value) {
        this.accessToken = value;
    }

    /**
     * Getter method.
     *
     * @return token type
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * Setter method.
     *
     * @param value token type
     */
    public void setTokenType(String value) {
        this.tokenType = value;
    }

}
