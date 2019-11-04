import Util.ComparablePair;
import Util.RBTree;

public class Edge implements EdgeInterface {
    Point[] endPoints;

    static RBTree<ComparablePair<Point, Point>, Edge> allEdges
        = new RBTree<>();

    public static Edge getEdge(Point p1, Point p2) {
        ComparablePair<Point, Point> pair;
        if (p1.compareTo(p2) <= 0) {
            pair = new ComparablePair<>(p1, p2);
        } else {
            pair = new ComparablePair<>(p2, p1);
        }

        RBTree.Node<ComparablePair<Point, Point>, Edge> node
        = allEdges.search(pair);

        if (node != null) {
            return node.getValue();
        }

        return new Edge(pair);
    }

    private Edge(ComparablePair<Point, Point> pair) {
        endPoints = new Point[]{pair.first, pair.second};
        allEdges.insert(pair, this);
    }

    public PointInterface[] edgeEndPoints() {
        return new PointInterface[]{endPoints[0], endPoints[1]};
    }
}