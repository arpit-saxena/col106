import static org.junit.Assert.*;
import org.junit.Test;

public class TestTriangle {

    @Test
    public void testConstruction() {
        BasicPoint p1 = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p2 = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p3 = new BasicPoint(0.0f, 0.0f, 1.0f);
        try {
            new Triangle(p1, p2, p3);
        } catch (Exception e) {
            fail();
        }

        boolean gotException = false;
        try {
            new Triangle(p1, p2);
        } catch (IllegalArgumentException e) {
            gotException = true;
        } catch (Exception e) {
            fail();
        }

        if (!gotException) fail();

        try {
            new Triangle(p1, p2, p3, p3);
        } catch (IllegalArgumentException e) {
            gotException = true;
        } catch (Exception e) {
            fail();
        }

        if (!gotException) fail();
    }

    /**
     * Assert that the formed triangle is valid
     */
    void assertValidTriangle(BasicPoint... basicPoints) {
        try {
            new Triangle(basicPoints);
        } catch (InvalidTriangleException e) {
            fail();
        }
    }

    void assertAllValidTriangle(BasicPoint p1, BasicPoint p2, BasicPoint p3) {
        assertValidTriangle(p1, p2, p3);
        assertValidTriangle(p1, p3, p2);
        assertValidTriangle(p2, p3, p1);
        assertValidTriangle(p2, p1, p3);
        assertValidTriangle(p3, p1, p2);
        assertValidTriangle(p3, p2, p1);
    }

    void assertInvalidTriangle(BasicPoint... basicPoints) {
        try {
            new Triangle(basicPoints);
        } catch (InvalidTriangleException e) {
            return;
        } catch (Exception e) {
            fail();
        }

        fail();
    }

    void assertAllInvalidTriangle(BasicPoint p1, BasicPoint p2, BasicPoint p3) {
        assertInvalidTriangle(p1, p2, p3);
        assertInvalidTriangle(p1, p3, p2);
        assertInvalidTriangle(p2, p3, p1);
        assertInvalidTriangle(p2, p1, p3);
        assertInvalidTriangle(p3, p1, p2);
        assertInvalidTriangle(p3, p2, p1);
    }

    @Test
    public void testValidTriangle() {
        BasicPoint p1 = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p2 = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p3 = new BasicPoint(0.0f, 0.0f, 1.0f);

        assertAllValidTriangle(p1, p2, p3);

        p3.z = 0.0f; p3.x = 0.5f; p3.y = 0.5f;
        assertAllInvalidTriangle(p1, p2, p3);
    }
}