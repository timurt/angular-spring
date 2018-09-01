package kz.iamtim.arm.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test class for {@code Role}.
 *
 * @author Timur Tibeyev.
 */
public class RoleTest {
    @Test
    public void testName() {
        final Role role = new Role();
        role.setName("ROLE_NAME");
        assertEquals("ROLE_NAME", role.getName());
    }

    @Test
    public void testKey() {
        final Role role = new Role();
        role.setKey("ROLE_KEY");
        assertEquals("ROLE_KEY", role.getKey());
    }
}
