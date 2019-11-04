import static org.junit.Assert.*;
import org.junit.Test;

public class TestEdge {

    @Test
    public void testBasic() {
        Point p1 = new Point(1.00f, 1.00f, 1.05f);
        Point p2 = new Point(1.03f, 1.00f, 1.05f);

        Edge edge = new Edge(p1, p2);
        assertArrayEquals(new Point[]{p1, p2}, edge.endPoints);
        assertArrayEquals(new PointInterface[]{p1, p2}, edge.edgeEndPoints());

        edge = new Edge(p2, p1);
        assertArrayEquals(new Point[]{p1, p2}, edge.endPoints);
        assertArrayEquals(new PointInterface[]{p1, p2}, edge.edgeEndPoints());

    }
}