import Util.ComparablePair;
import Util.Comparator;
import Util.LinkedList;
import Util.ArrayList;
import Util.RBTree;

public class Edge implements EdgeInterface {
    Point[] endPoints;

    static RBTree<ComparablePair<Point, Point>, Edge> allEdges
        = new RBTree<>();
    static LinkedList<Edge> boundaryEdges = new LinkedList<>();

    // Max number of neighbor triangles of an edge
    static int maxTrianglesNeighbor = 0;

    public static void setTrianglesNeighborStats(int count) {
        if (count > maxTrianglesNeighbor) {
            maxTrianglesNeighbor = count;
        }
    }

    public static int typeMesh() {
        if (maxTrianglesNeighbor > 2)return 3; // Improper
        if (boundaryEdges.size() > 0) return 2; // Semi-proper
        if (maxTrianglesNeighbor == 2) return 1; // Proper
        return 0; // Not defined
    } 

    public static void resetStatics() {
        allEdges = new RBTree<>();
        boundaryEdges = new LinkedList<>();
        maxTrianglesNeighbor = 0;
    }

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

    public ArrayList<Triangle> neighborTriangles = new ArrayList<>();
    public ConnectedComponent component = new ConnectedComponent();

    // node in boundary linked list if this edge forms a boundary
    // else null
    public LinkedList<Edge>.Node boundaryNode = null;

    private Edge(ComparablePair<Point, Point> pair) {
        endPoints = new Point[]{pair.first, pair.second};
        allEdges.insert(pair, this);
        pair.first.neighborPoints.add(pair.second);
        pair.second.neighborPoints.add(pair.first);
        pair.first.neighborEdges.add(this);
        pair.second.neighborEdges.add(this);

        Comparator<ConnectedComponent> comparator = (a, b) -> {
            return a.equals(b) ? 0 : 1;
        };
        if (pair.first.components.addIfNotExists(component, comparator)) {
            component.infoLatest = false;
        }
        if (pair.second.components.addIfNotExists(component, comparator)) {
            component.infoLatest = false;
        }
    }

    public PointInterface[] edgeEndPoints() {
        return new PointInterface[]{endPoints[0], endPoints[1]};
    }
}