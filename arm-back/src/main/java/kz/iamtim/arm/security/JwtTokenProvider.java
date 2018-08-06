package kz.iamtim.arm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import kz.iamtim.arm.payload.JwtAuthenticationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import java.util.Date;
import org.springframework.stereotype.Component;

/**
 * JWT token provider.
 *
 * @author Timur Tibeyev.
 */
@Component
public class JwtTokenProvider {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    /** JWT secret key. */
    @Value("${app.jwtSecret}")
    private String jwtSecret;

    /** JWT expiration time. */
    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    /**
     * Generates JWT token.
     *
     * @param authentication authentication
     *
     * @return JWT token response
     */
    public JwtAuthenticationResponse generateToken(Authentication authentication) {

        MyUserPrincipal myUserPrincipal = (MyUserPrincipal) authentication.getPrincipal();

        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        String jwt = Jwts.builder()
                .setSubject(Long.toString(myUserPrincipal.getId()))
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        JwtAuthenticationResponse res = new JwtAuthenticationResponse(jwt, expiryDate.getTime());

        return res;
    }

    /**
     * Extract user id from JWT token.
     *
     * @param token access token
     *
     * @return user id
     */
    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    /**
     * Checks if token is valid.
     *
     * @param authToken access token
     *
     * @return if token is valid
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            LOGGER.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            LOGGER.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOGGER.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOGGER.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOGGER.error("JWT claims string is empty.");
        }
        return false;
    }

}
