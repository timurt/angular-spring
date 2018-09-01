package kz.iamtim.arm.models;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for {@code User}.
 *
 * @author Timur Tibeyev.
 */
public class UserTest {
    @Test
    public void testName() {
        final User user = new User();
        user.setName("USER_NAME");
        assertEquals("USER_NAME", user.getName());
    }

    @Test
    public void testLogin() {
        final User user = new User();
        user.setLogin("USER_LOGIN");
        assertEquals("USER_LOGIN", user.getLogin());
    }

    @Test
    public void testPassword() {
        final User user = new User();
        user.setPassword("USER_PASSWORD");
        assertEquals("USER_PASSWORD", user.getPassword());
    }

    @Test
    public void testRole() {
        final Role role = new Role();
        role.setKey("ROLE_KEY");
        role.setName("ROLE_NAME");

        final User user = new User();
        user.setRole(role);
        assertNotNull(user.getRole());
        assertEquals("ROLE_KEY", user.getRole().getKey());
        assertEquals("ROLE_NAME", user.getRole().getName());
    }

    @Test
    public void testId() {
        final User user = new User();
        user.setId(123L);
        long actual = user.getId();
        assertEquals(123L, actual);
    }

    @Test
    public void testCreatedAt() {
        final LocalDateTime date = LocalDateTime.now();
        final User user = new User();
        user.setCreatedAt(date);
        assertEquals(date, user.getCreatedAt());
    }

    @Test
    public void testUpdatedAt() {
        final LocalDateTime date = LocalDateTime.now();
        final User user = new User();
        user.setUpdatedAt(date);
        assertEquals(date, user.getUpdatedAt());
    }

    @Test
    public void testDeletedAt() {
        final LocalDateTime date = LocalDateTime.now();
        final User user = new User();
        user.setDeletedAt(date);
        assertEquals(date, user.getDeletedAt());
    }
}
