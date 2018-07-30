package kz.iamtim.arm.model;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test class for testing {@code Panel} class.
 *
 * @author Timur Tibeyev.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PanelTest {

//    private static final double DELTA = 1e-15;
//
//    @Test
//    public void testEqualsOne() {
//        Panel a = new Panel();
//        a.setBrand("Tesla");
//        a.setId(123l);
//        a.setSerial("2323232323232323");
//
//        Assert.assertFalse(a.equals(null));
//        Assert.assertFalse(a.equals(this));
//
//        Panel b = a;
//        Assert.assertTrue(a.equals(b));
//
//        Panel c = new Panel();
//        c.setBrand("Tesla");
//        c.setId(123l);
//        c.setSerial("2323232323232323");
//
//        Assert.assertTrue(a.equals(c));
//    }
//
//    @Test
//    public void testEqualsTwo() {
//        Panel a = new Panel();
//        Panel b = new Panel();
//        Assert.assertTrue(a.equals(b));
//
//        b.setBrand("Tesla");
//        Assert.assertFalse(a.equals(b));
//
//        a.setBrand("Ford");
//        Assert.assertFalse(a.equals(b));
//
//        a.setBrand("Tesla");
//        Assert.assertTrue(a.equals(b));
//
//        b.setId(123l);
//        Assert.assertFalse(a.equals(b));
//
//        a.setId(234l);
//        Assert.assertFalse(a.equals(b));
//
//        a.setId(123l);
//        Assert.assertTrue(a.equals(b));
//
//        b.setSerial("2323232323232323");
//        Assert.assertFalse(a.equals(b));
//
//        a.setSerial("3434343434343434");
//        Assert.assertFalse(a.equals(b));
//
//        a.setSerial("2323232323232323");
//        Assert.assertTrue(a.equals(b));
//    }
//
//    @Test
//    public void testHashCode() {
//        Panel a = new Panel();
//        Panel b = new Panel();
//        Assert.assertEquals(a.hashCode(), b.hashCode());
//
//        b.setBrand("Tesla");
//        Assert.assertNotEquals(a.hashCode(), b.hashCode());
//
//        a.setBrand("Ford");
//        Assert.assertNotEquals(a.hashCode(), b.hashCode());
//
//        a.setBrand("Tesla");
//        Assert.assertEquals(a.hashCode(), b.hashCode());
//
//        b.setId(123l);
//        Assert.assertNotEquals(a.hashCode(), b.hashCode());
//
//        a.setId(234l);
//        Assert.assertNotEquals(a.hashCode(), b.hashCode());
//
//        a.setId(123l);
//        Assert.assertEquals(a.hashCode(), b.hashCode());
//
//        b.setSerial("2323232323232323");
//        Assert.assertNotEquals(a.hashCode(), b.hashCode());
//
//        a.setSerial("3434343434343434");
//        Assert.assertNotEquals(a.hashCode(), b.hashCode());
//
//        a.setSerial("2323232323232323");
//        Assert.assertEquals(a.hashCode(), b.hashCode());
//    }
//
//    @Test
//    public void testToString() {
//        Panel p = new Panel();
//        p.setId(123l);
//        p.setSerial("2323232323232323");
//        p.setLatitude(34.123);
//        p.setLongitude(45.567);
//        p.setBrand("Tesla");
//
//        String expected = "Panel [id=123, serial=2323232323232323, longitude=45.567, "
//                + "latitude=34.123, brand=Tesla]";
//
//        Assert.assertEquals(expected, p.toString());
//    }
//
//    @Test
//    public void testGetters() {
//        Panel p = new Panel();
//        p.setSerial("2323232323232323");
//        p.setLatitude(34.123);
//        p.setLongitude(45.567);
//        p.setBrand("Tesla");
//
//        Assert.assertEquals("2323232323232323", p.getSerial());
//        Assert.assertEquals(34.123, p.getLatitude().doubleValue(), DELTA);
//        Assert.assertEquals(45.567, p.getLongitude().doubleValue(), DELTA);
//        Assert.assertEquals("Tesla", p.getBrand());
//    }

}
