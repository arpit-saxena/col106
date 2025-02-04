import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;

import static org.hamcrest.Matchers.*;

import org.hamcrest.collection.IsArrayContainingInAnyOrder;
import org.hamcrest.core.CombinableMatcher;
import org.junit.Before;
import org.junit.Test;

public class TestTriangle {

    @Before
    public void reset() {
        Point.resetStatics();
        Edge.resetStatics();
        Triangle.resetStatics();
        ConnectedComponent.resetStatics();
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
        assertArrayEquals(new Triangle[]{}, t.extendedNeighborTriangles().toArray());

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
        assertArrayEquals(new Triangle[]{}, t1.extendedNeighborTriangles().toArray());
        assertEquals(0, t1.getDiameter());

        Triangle t2 = new Triangle(p2, p4, p5);

        assertTrue(
            Triangle.allTriangles.search(
                new BasicTriangle(p2, p5, p4)
            ).getValue() == t2
        );
        assertArrayEquals(new Triangle[]{}, t1.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t2}, t1.extendedNeighborTriangles().toArray());
        assertArrayEquals(new Triangle[]{}, t2.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1}, t2.extendedNeighborTriangles().toArray());
        assertEquals(0, t1.getDiameter());
        assertEquals(0, t2.getDiameter());

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
        assertArrayEquals(new Triangle[]{t2, t3}, t1.extendedNeighborTriangles().toArray());
        assertArrayEquals(new Triangle[]{}, t2.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t3}, t2.extendedNeighborTriangles().toArray());
        assertArrayEquals(new Triangle[]{}, t3.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t2}, t3.extendedNeighborTriangles().toArray());
        assertEquals(0, t1.getDiameter());
        assertEquals(0, t2.getDiameter());
        assertEquals(0, t3.getDiameter());

        Triangle t4 = new Triangle(p3, p2, p5);

        assertTrue(
            Triangle.allTriangles.search(
                new BasicTriangle(p5, p2, p3)
            ).getValue() == t4
        );
        assertArrayEquals(new Triangle[]{t4}, t1.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t2, t3, t4}, t1.extendedNeighborTriangles().toArray());
        assertArrayEquals(new Triangle[]{t4}, t2.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t3, t4}, t2.extendedNeighborTriangles().toArray());
        assertArrayEquals(new Triangle[]{t4}, t3.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t2, t4}, t3.extendedNeighborTriangles().toArray());
        assertArrayEquals(new Triangle[]{t1, t2, t3}, t4.neighborTriangles.toArray());
        assertArrayEquals(new Triangle[]{t1, t2, t3}, t4.extendedNeighborTriangles().toArray());
        assertEquals(2, t1.getDiameter());
        assertEquals(2, t2.getDiameter());
        assertEquals(2, t3.getDiameter());
        assertEquals(2, t4.getDiameter());
    }

    /**
     *      
     *         . p6
     * p3 .            .p5
     * p1 .    . p2    .p4
     */
    @Test
    public void testBoundary() {
        BasicPoint p1 = new BasicPoint(0.0f, 0.0f, 0.0f);
        BasicPoint p2 = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p3 = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p4 = new BasicPoint(2.0f, 0.0f, 0.0f);
        BasicPoint p5 = new BasicPoint(2.0f, 1.0f, 0.0f);
        BasicPoint p6 = new BasicPoint(1.0f, 2.0f, 0.0f);

        Triangle t1 = new Triangle(p1, p2, p3);
        assertEquals(1, Edge.maxTrianglesNeighbor);
        assertEquals(2, Edge.typeMesh());

        Triangle t2 = new Triangle(p2, p4, p5);
        assertEquals(1, Edge.maxTrianglesNeighbor);
        assertEquals(2, Edge.typeMesh());

        Triangle t3 = new Triangle(p3, p5, p6);
        assertEquals(1, Edge.maxTrianglesNeighbor);
        assertEquals(2, Edge.typeMesh());

        Triangle t4 = new Triangle(p3, p2, p5);
        assertEquals(2, Edge.maxTrianglesNeighbor);
        assertEquals(2, Edge.typeMesh());
    }

    @Test
    public void testBoundary2() {
        BasicPoint p1 = new BasicPoint(0.0f, 0.0f, 0.0f);
        BasicPoint p2 = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p3 = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p4 = new BasicPoint(0.0f, 0.0f, 1.0f);
        
        new Triangle(p1, p2, p3);
        assertEquals(1, Edge.maxTrianglesNeighbor);
        assertEquals(2, Edge.typeMesh());

        new Triangle(p1, p2, p4);
        assertEquals(2, Edge.maxTrianglesNeighbor);
        assertEquals(2, Edge.typeMesh());        

        new Triangle(p2, p3, p4);
        assertEquals(2, Edge.maxTrianglesNeighbor);
        assertEquals(2, Edge.typeMesh());        

        new Triangle(p1, p3, p4);
        assertEquals(2, Edge.maxTrianglesNeighbor);
        assertEquals(1, Edge.typeMesh());        

        BasicPoint p5 = new BasicPoint(0.0f, 0.0f, -1.0f);
        new Triangle(p1, p2, p5);
        assertEquals(3, Edge.maxTrianglesNeighbor);
        assertEquals(3, Edge.typeMesh());
    }

    /**
     *      
     *         . p6
     * p3 .            .p5
     * p1 .    . p2    .p4
     */
    @Test
    public void testConnectedComponentCount() {
        BasicPoint p1 = new BasicPoint(0.0f, 0.0f, 0.0f);
        BasicPoint p2 = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p3 = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p4 = new BasicPoint(2.0f, 0.0f, 0.0f);
        BasicPoint p5 = new BasicPoint(2.0f, 1.0f, 0.0f);
        BasicPoint p6 = new BasicPoint(1.0f, 2.0f, 0.0f);

        Triangle t1 = new Triangle(p1, p2, p3);
        assertEquals(1, ConnectedComponent.count);
        for (Edge edge : t1.edges) {
            assertEquals(t1.component, edge.component);
        }

        Triangle t2 = new Triangle(p2, p4, p5);
        assertEquals(2, ConnectedComponent.count);

        Triangle t3 = new Triangle(p3, p5, p6);
        assertEquals(3, ConnectedComponent.count);

        Triangle t4 = new Triangle(p3, p2, p5);
        assertEquals(1, ConnectedComponent.count);
        assertEquals(t1.component, t2.component);
        assertEquals(t1.component, t2.component);
        assertEquals(t1.component, t3.component);
        assertEquals(t1.component, t4.component);

    }

    void assertPointEqual(BasicPoint p1, BasicPoint p2, float delta) {
        assertEquals(p1.x, p2.x, delta);
        assertEquals(p1.y, p2.y, delta);
        assertEquals(p1.z, p2.z, delta);
    }

    /**
     *      
     *         . p6
     * p3 .            .p5
     * p1 .    . p2    .p4
     */
    @Test
    public void testCentroid() {
        BasicPoint p1 = new BasicPoint(0.0f, 0.0f, 0.0f);
        BasicPoint p2 = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p3 = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p4 = new BasicPoint(2.0f, 0.0f, 0.0f);
        BasicPoint p5 = new BasicPoint(2.0f, 1.0f, 0.0f);
        BasicPoint p6 = new BasicPoint(1.0f, 2.0f, 0.0f);

        Triangle t1 = new Triangle(p1, p2, p3);
        assertPointEqual(
            new BasicPoint(1.0f / 3.0f, 1.0f / 3.0f, 0.0f),
            t1.component.centroid(),
            0.00001f
        );
        assertEquals(3, t1.component.numNodes());
        assertEquals(1, ConnectedComponent.allCentroids().length);
        assertPointEqual(
            new BasicPoint(1.0f / 3.0f, 1.0f / 3.0f, 0.0f),
            ConnectedComponent.allCentroids()[0],
            0.00001f
        );
        

        Triangle t2 = new Triangle(p2, p4, p5);
        assertPointEqual(
            new BasicPoint(1.0f / 3.0f, 1.0f / 3.0f, 0.0f),
            t1.component.centroid(),
            0.00001f
        );
        assertEquals(3, t1.component.numNodes());
        assertPointEqual(
            new BasicPoint(5.0f / 3, 1.0f / 3, 0.0f / 3),
            t2.component.centroid(),
            0.00001f
        );
        assertEquals(3, t2.component.numNodes());
        assertEquals(2, ConnectedComponent.allCentroids().length);
        assertPointEqual(
            new BasicPoint(1.0f / 3.0f, 1.0f / 3.0f, 0.0f),
            ConnectedComponent.allCentroids()[0],
            0.00001f
        );
        assertPointEqual(            
            new BasicPoint(5.0f / 3, 1.0f / 3, 0.0f / 3),
            ConnectedComponent.allCentroids()[1],
            0.00001f
        );


        Triangle t3 = new Triangle(p3, p5, p6);
        assertPointEqual(
            new BasicPoint(1.0f / 3.0f, 1.0f / 3.0f, 0.0f),
            t1.component.centroid(),
            0.00001f
        );
        assertEquals(3, t1.component.numNodes());
        assertPointEqual(
            new BasicPoint(5.0f / 3, 1.0f / 3, 0.0f / 3),
            t2.component.centroid(),
            0.00001f
        );
        assertEquals(3, t3.component.numNodes());
        assertPointEqual(
            new BasicPoint(3.0f / 3, 4.0f / 3, 0.0f / 3),
            t3.component.centroid(),
            0.00001f
        );
        assertEquals(3, t3.component.numNodes());
        assertEquals(3, ConnectedComponent.allCentroids().length);
        assertPointEqual(
            new BasicPoint(1.0f / 3.0f, 1.0f / 3.0f, 0.0f),
            ConnectedComponent.allCentroids()[0],
            0.00001f
        );
        assertPointEqual(
            new BasicPoint(3.0f / 3, 4.0f / 3, 0.0f / 3),
            ConnectedComponent.allCentroids()[1],            
            0.00001f
        );
        assertPointEqual(            
            new BasicPoint(5.0f / 3, 1.0f / 3, 0.0f / 3),
            ConnectedComponent.allCentroids()[2],
            0.00001f
        );

        Triangle t4 = new Triangle(p3, p2, p5);
        assertPointEqual(
            new BasicPoint(6.0f / 6, 4.0f / 6, 0.0f),
            t1.component.centroid(),
            0.00001f
        );
        assertEquals(6, t1.component.numNodes());
        assertEquals(6, t4.component.numNodes());
        assertEquals(1, ConnectedComponent.allCentroids().length);
        assertPointEqual(
            new BasicPoint(6.0f / 6, 4.0f / 6, 0.0f),            
            ConnectedComponent.allCentroids()[0],
            0.00001f
        );
    }

    void assertTriangleInLater(Triangle t1, Triangle... list) {
        if (list.length == 0) fail();
        if (list.length == 1) {
            assertThat(t1, is(list[0]));
            return;
        }
        CombinableMatcher<Triangle> matcher
            = either(is(list[0])).or(is(list[1]));

        for (int i = 2; i < list.length; i++) {
            matcher = matcher.or(is(list[i]));
        }
        assertThat(t1, matcher);
    }

    /**
     * p4 .    . p8  
     * p3 .    . p7 
     * p2 .    . p6 
     * p1 .    . p5 
     */
    @Test
    public void testRepTriangleAndTriangles() {
        BasicPoint p1  = new BasicPoint(0.0f, 0.0f, 0.0f);
        BasicPoint p2  = new BasicPoint(0.0f, 1.0f, 0.0f);
        BasicPoint p3  = new BasicPoint(0.0f, 2.0f, 0.0f);
        BasicPoint p4  = new BasicPoint(0.0f, 3.0f, 0.0f);
        BasicPoint p5  = new BasicPoint(1.0f, 0.0f, 0.0f);
        BasicPoint p6  = new BasicPoint(1.0f, 1.0f, 0.0f);
        BasicPoint p7  = new BasicPoint(1.0f, 2.0f, 0.0f);
        BasicPoint p8  = new BasicPoint(1.0f, 3.0f, 0.0f);

        Triangle t1 = new Triangle(p1, p2, p5);
        ConnectedComponent comp1 = t1.component;
        assertEquals(comp1.numTriangles(), 1);
        assertArrayEquals(new Triangle[]{t1}, comp1.triangles().toArray());
        assertEquals(ConnectedComponent.maxTriangles, 1);
        assertEquals(ConnectedComponent.maxTriangleComp, comp1);

        Triangle t2 = new Triangle(p2, p5, p6);
        assertEquals(comp1.numTriangles(), 2);
        assertArrayEquals(new Triangle[]{t1, t2}, comp1.triangles().toArray());
        assertEquals(ConnectedComponent.maxTriangles, 2);

        Triangle t3 = new Triangle(p2, p3, p6);
        assertEquals(comp1.numTriangles(), 3);
        assertArrayEquals(new Triangle[]{t1, t2, t3}, comp1.triangles().toArray());
        assertEquals(ConnectedComponent.maxTriangles, 3);

        Triangle t4 = new Triangle(p3, p8, p7);
        ConnectedComponent comp2 = t4.component;
        assertEquals(comp1.numTriangles(), 3);
        assertArrayEquals(new Triangle[]{t1, t2, t3}, comp1.triangles().toArray());
        assertEquals(comp2.numTriangles(), 1);
        assertArrayEquals(new Triangle[]{t4}, comp2.triangles().toArray());
        assertEquals(ConnectedComponent.maxTriangles, 3);

        assertTriangleInLater(comp1.repTriangle(), t1, t2, t3);
        assertTriangleInLater(comp2.repTriangle(), t4);
        
        Triangle t5 = new Triangle(p3, p6, p7);
        assertEquals(comp1, comp2);
        assertEquals(ConnectedComponent.maxTriangleComp, comp1);
        assertEquals(comp1.numTriangles(), 5);
        Triangle[] t = new Triangle[comp1.triangles().size()];
        comp1.triangles().copyToArray(t);
        assertThat(
            Arrays.asList(t),
            containsInAnyOrder(t1, t2, t3, t4, t5)
        );
        assertEquals(comp2.numTriangles(), 5);
        assertEquals(ConnectedComponent.maxTriangles, 5);

        assertTriangleInLater(comp1.repTriangle(), t1, t2, t3, t4, t5);
    }
}