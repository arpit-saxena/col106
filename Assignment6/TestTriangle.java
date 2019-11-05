import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestTriangle {

    @Before
    public void reset() {
        Point.resetStatics();
        Edge.resetStatics();
        Triangle.resetStatics();
    }

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

    @Test
    public void testPointOrder() {
        BasicPoint p1 = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p2 = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p3 = new BasicPoint(0.0f, 0.0f, 1.0f);

        Triangle t = new Triangle(p1, p2, p3);

        assertTrue(t.vertices[0].compareTo(p3) == 0);
        assertTrue(t.vertices[1].compareTo(p2) == 0);
        assertTrue(t.vertices[2].compareTo(p1) == 0);

        p3 = new BasicPoint(1.0f, 0.0f, -1.0f);
        t = new Triangle(p1, p2, p3);

        assertTrue(t.vertices[0].compareTo(p2) == 0);
        assertTrue(t.vertices[1].compareTo(p3) == 0);
        assertTrue(t.vertices[2].compareTo(p1) == 0);
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

    @Test
    public void testVertexEdgeLists() {
        BasicPoint p1 = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p2 = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p3 = new BasicPoint(0.0f, 0.0f, 1.0f);

        Triangle t = new Triangle(p1, p2, p3);
        Point[] p = t.vertices;
        Edge[] e = t.edges;

        Triangle[] tArr = new Triangle[]{t};
        for (int i = 0; i < 3; i++) {
            assertArrayEquals(tArr, p[i].neighborTriangles.toArray());
            assertArrayEquals(tArr, e[i].neighborTriangles.toArray());
        }
        assertArrayEquals(new Triangle[]{}, t.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{}, t.extendedNeighborTriangles.toArray());

        BasicPoint p4 = new BasicPoint(0.0f, 0.0f, 0.0f);
        Triangle t2 = new Triangle(p4, p2, p3);
        p = t2.vertices;
        e = t2.edges;
        assertArrayEquals(new Triangle[]{t2}, p[0].neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t, t2}, p[1].neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t, t2}, p[2].neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t, t2}, e[0].neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t2}, e[1].neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t2}, e[2].neighborTriangles.toArray());
    }

    /**
     *      
     *         . p6
     * p3 .            .p5
     * p1 .    . p2    .p4
     */
    @Test
    public void testNeighborLists() {
        BasicPoint p1 = new BasicPoint(0.0f, 0.0f, 0.0f);
        BasicPoint p2 = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p3 = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p4 = new BasicPoint(2.0f, 0.0f, 0.0f);
        BasicPoint p5 = new BasicPoint(2.0f, 1.0f, 0.0f);
        BasicPoint p6 = new BasicPoint(1.0f, 2.0f, 0.0f);

        Triangle t1 = new Triangle(p1, p2, p3);

        assertTrue(
            Triangle.allTriangles.search(
                new BasicTriangle(p1, p2, p3)
            ).getValue() == t1
        );
        assertArrayEquals(new Triangle[]{}, t1.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{}, t1.extendedNeighborTriangles.toArray());

        Triangle t2 = new Triangle(p2, p4, p5);

        assertTrue(
            Triangle.allTriangles.search(
                new BasicTriangle(p2, p5, p4)
            ).getValue() == t2
        );
        assertArrayEquals(new Triangle[]{}, t1.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t2}, t1.extendedNeighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{}, t2.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1}, t2.extendedNeighborTriangles.toArray());

        Triangle t3 = new Triangle(p3, p5, p6);

        assertTrue(
            Triangle.allTriangles.search(
                new BasicTriangle(p2, p1, p3)
            ).getValue() == t1
        );
        assertTrue(
            Triangle.allTriangles.search(
                new BasicTriangle(p5, p3, p6)
            ).getValue() == t3
        );
        assertArrayEquals(new Triangle[]{}, t1.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t2, t3}, t1.extendedNeighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{}, t2.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t3}, t2.extendedNeighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{}, t3.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t2}, t3.extendedNeighborTriangles.toArray());

        Triangle t4 = new Triangle(p3, p2, p5);

        assertTrue(
            Triangle.allTriangles.search(
                new BasicTriangle(p5, p2, p3)
            ).getValue() == t4
        );
        assertArrayEquals(new Triangle[]{t4}, t1.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t2, t3, t4}, t1.extendedNeighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t4}, t2.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t3, t4}, t2.extendedNeighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t4}, t3.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t2, t4}, t3.extendedNeighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t2, t3}, t4.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t2, t3}, t4.extendedNeighborTriangles.toArray());
    }
}