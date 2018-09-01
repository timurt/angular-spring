package kz.iamtim.arm.payload;

import kz.iamtim.arm.models.Restaurant;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@code RestaurantDto}.
 *
 * @author Timur Tibeyev.
 */
public class RestaurantDtoTest {
    @Test
    public void testHasWifi() {
        final RestaurantDto dto = new RestaurantDto();
        dto.setHasWifi(true);
        assertTrue(dto.isHasWifi());

        dto.setHasWifi(false);
        assertFalse(dto.isHasWifi());
    }

    @Test
    public void testOwnerName() {
        final RestaurantDto dto = new RestaurantDto();
        dto.setOwnerName("OWNER_NAME");
        assertEquals("OWNER_NAME", dto.getOwnerName());
    }

    @Test
    public void testCreatedAt() {
        final LocalDateTime date = LocalDateTime.now();
        final RestaurantDto dto = new RestaurantDto();
        dto.setCreatedAt(date);
        assertEquals(date, dto.getCreatedAt());
    }

    @Test
    public void testConstructor() {
        final Restaurant restaurant = new Restaurant();
        final RestaurantDto dto = new RestaurantDto(restaurant);

        assertNull(dto.getOwnerId());
        assertNull(dto.getOwnerName());
    }
}
