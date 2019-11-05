import static org.junit.Assert.*;
import org.junit.Test;

public class TestEdge {

    @Test
    public void testBasic() {
        Point p1 = Point.getPoint(1.00f, 1.00f, 1.05f);
        Point p2 = Point.getPoint(1.03f, 1.00f, 1.05f);

        Edge edge = Edge.getEdge(p1, p2);
        assertArrayEquals(new Point[]{p1, p2}, edge.endPoints);
        assertArrayEquals(new PointInterface[]{p1, p2}, edge.edgeEndPoints());

        edge = Edge.getEdge(p2, p1);
        assertArrayEquals(new Point[]{p1, p2}, edge.endPoints);
        assertArrayEquals(new PointInterface[]{p1, p2}, edge.edgeEndPoints());
    }

    @Test
    public void testGetEdge() {
        Point p1 = Point.getPoint(new BasicPoint(1.0f, 1.0f, 1.0f));
        Point p2 = Point.getPoint(new BasicPoint(1.5f, 1.0f, 1.0f));
        Edge edge = Edge.getEdge(p1, p2);
        assertNotNull(edge);

        Edge edge2 = Edge.getEdge(p1, p2);
        assertSame(edge, edge2);

        Point p3 = Point.getPoint(new BasicPoint(1.5f, 2.0f, 1.0f));
        Edge edge3 = Edge.getEdge(p1, p3);
        assertNotNull(edge3);

        assertSame(edge, Edge.getEdge(p1, p2));
    }

    @Test
    public void testVertexLists() {
        Point p1 = Point.getPoint(new BasicPoint(0.0f, 0.0f, 0.0f));
        Point p2 = Point.getPoint(new BasicPoint(1.0f, 0.0f, 0.0f));

        Edge e1 = Edge.getEdge(p1, p2);

        assertArrayEquals(new Point[]{p2}, p1.neighborPoints.toArray());
        assertArrayEquals(new Point[]{p1}, p2.neighborPoints.toArray());
        assertArrayEquals(new Edge[]{e1}, p1.neighborEdges.toArray());
        assertArrayEquals(new Edge[]{e1}, p2.neighborEdges.toArray());

        Point p3 = Point.getPoint(new BasicPoint(0.0f, 1.0f, 0.0f));
        Edge e2 = Edge.getEdge(p2, p3);

        assertArrayEquals(new Point[]{p2}, p1.neighborPoints.toArray());
        assertArrayEquals(new Point[]{p1, p3}, p2.neighborPoints.toArray());
        assertArrayEquals(new Point[]{p2}, p3.neighborPoints.toArray());
        assertArrayEquals(new Edge[]{e1}, p1.neighborEdges.toArray());
        assertArrayEquals(new Edge[]{e1, e2}, p2.neighborEdges.toArray());
        assertArrayEquals(new Edge[]{e2}, p3.neighborEdges.toArray());

        Edge e3 = Edge.getEdge(p1, p3);
        assertArrayEquals(new Point[]{p2, p3}, p1.neighborPoints.toArray());
        assertArrayEquals(new Point[]{p1, p3}, p2.neighborPoints.toArray());
        assertArrayEquals(new Point[]{p2, p1}, p3.neighborPoints.toArray());
        assertArrayEquals(new Edge[]{e1, e3}, p1.neighborEdges.toArray());
        assertArrayEquals(new Edge[]{e1, e2}, p2.neighborEdges.toArray());
        assertArrayEquals(new Edge[]{e2, e3}, p3.neighborEdges.toArray());
    }
}