package kz.iamtim.arm.payload;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@code ErrorResponse}.
 *
 * @author Timur Tibeyev.
 */
public class ErrorResponseTest {

    @Test
    public void testMessage() {
        final ErrorResponse errorResponse = new ErrorResponse("ERROR_MESSAGE");
        assertEquals("ERROR_MESSAGE", errorResponse.getMessage());

        errorResponse.setMessage("NEW_MESSAGE");
        assertEquals("NEW_MESSAGE", errorResponse.getMessage());
    }
}
