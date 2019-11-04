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
}