package kz.iamtim.arm.payload;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@code JwtAuthenticationResponse}.
 *
 * @author Timur Tibeyev.
 */
public class JwtAuthenticationResponseTest {
    @Test
    public void testAccessToken() {
        final JwtAuthenticationResponse response =
                new JwtAuthenticationResponse("ACCESS_TOKEN", 123L);
        assertEquals("ACCESS_TOKEN", response.getAccessToken());
        response.setAccessToken("NEW_ACCESS_TOKEN");
        assertEquals("NEW_ACCESS_TOKEN", response.getAccessToken());
    }

    @Test
    public void testExpirationTime() {
        final JwtAuthenticationResponse response =
                new JwtAuthenticationResponse("ACCESS_TOKEN", 123L);
        final long actual = response.getExpirationTime();
        assertEquals(123L, actual);

        response.setExpirationTime(345L);
        final long actual2 = response.getExpirationTime();
        assertEquals(345L, actual2);
    }

    @Test
    public void testTokenType() {
        final JwtAuthenticationResponse response =
                new JwtAuthenticationResponse("ACCESS_TOKEN", 123L);
        response.setTokenType("TOKEN_TYPE");
        assertEquals("TOKEN_TYPE", response.getTokenType());
    }
}
