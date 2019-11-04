import static org.junit.Assert.*;
import org.junit.Test;

public class TestPoint {
    
    @Test
    public void testInterface() {
        BasicPoint point = new BasicPoint(1.0f, 1.1f, 1.2f);
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
        BasicPoint p1 = new BasicPoint(1.00f, 1.00f, 1.00f);
        BasicPoint p2 = new BasicPoint(1.01f, 1.00f, 1.00f);
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

    @Test
    public void testGetPoint(){
        Point point = Point.getPoint(new BasicPoint(1.0f, 1.0f, 1.0f));
        assertNotNull(point);

        Point point2 = Point.getPoint(new BasicPoint(1.0f, 1.0f, 1.0f));
        assertSame(point, point2);

        Point point3 = Point.getPoint(new BasicPoint(1.0f, 1.1f, 1.0f));
        assertNotNull(point3);

        assertSame(point, Point.getPoint(new BasicPoint(1.0f, 1.0f, 1.0f)));
    }
}