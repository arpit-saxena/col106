import static org.junit.Assert.*;
import org.junit.Test;

public class TestPoint {
    
    @Test
    public void testInterface() {
        Point point = new Point(1.0f, 1.1f, 1.2f);
        assertEquals(1.0f, point.getX(), 0.0001f);
        assertEquals(1.1f, point.getY(), 0.0001f);
        assertEquals(1.2f, point.getZ(), 0.0001f);
        assertArrayEquals(
            new float[]{1.0f, 1.1f, 1.2f}, 
            point.getXYZcoordinate(),
            0.00001f
        );
    }

    @Test
    public void testCompare() {
        Point p1 = new Point(1.00f, 1.00f, 1.00f);
        Point p2 = new Point(1.01f, 1.00f, 1.00f);
        assertTrue(p1.compareTo(p2) < 0);
        p2.y = 1.04f;
        assertTrue(p1.compareTo(p2) < 0);
        p2.y = 0.99f; p2.z = 0.99f;
        assertTrue(p1.compareTo(p2) < 0);

        p2.x = 0.99f;
        assertTrue(p1.compareTo(p2) > 0);

        p2.x = 1.00f; p2.y = 1.00f; p2.z = 1.00f;
        assertTrue(p1.compareTo(p2) == 0);
        
        p2.y = 1.01f; assertTrue(p1.compareTo(p2) < 0);
        p2.y = 0.99f; assertTrue(p1.compareTo(p2) > 0);

        p2.y = 1.00f;
        p2.z = 1.01f; assertTrue(p1.compareTo(p2) < 0);
        p2.z = 0.99f; assertTrue(p1.compareTo(p2) > 0);
    }
}