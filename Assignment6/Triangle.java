import Util.ArrayList;
import Util.ComparableTriple;
import Util.LinkedList;
import Util.RBTree;

class WrongNumberArgsException extends IllegalArgumentException {
    public WrongNumberArgsException(String s) {super(s);}
}

class InvalidTriangleException extends IllegalArgumentException {
    public InvalidTriangleException(String s) {super(s);}
}

class BasicTriangle extends ComparableTriple<BasicPoint, BasicPoint, BasicPoint> {
    BasicTriangle(BasicPoint... points) {
        super();
        ArrayList.bubbleSort(points);
        first = points[0];
        second = points[1];
        third = points[2];
    }
}

public class Triangle implements Comparable<Triangle>, TriangleInterface {
    Point[] vertices;
    Edge[] edges;
    int creationTime;
    ConnectedComponent component = new ConnectedComponent();

    ArrayList<Triangle> neighborTriangles = new ArrayList<>();
    private static int globalCounter = 0;
    public static RBTree<BasicTriangle, Triangle> allTriangles
        = new RBTree<>();

    public static void resetStatics() {
        globalCounter = 0;
        allTriangles = new RBTree<>();
    }

    public static Triangle getTriangle(BasicPoint p1, BasicPoint p2, BasicPoint p3) {
        BasicTriangle bt = new BasicTriangle(p1, p2, p3);
        RBTree.Node<BasicTriangle, Triangle> node
            = allTriangles.search(bt);
        if (node == null) return null;
        return node.getValue();
    }

    public Triangle(BasicPoint... basicPoints) {
        creationTime = globalCounter++;
        if (basicPoints.length != 3) {
            throw new WrongNumberArgsException(
                "Expected 3 points, got " + basicPoints.length
            );
        } else if (!checkValid(basicPoints)) {
            throw new InvalidTriangleException(
                "Given points don't form a valid triangle"
            );
        }

        // BasicTriangle's constructor sorts
        allTriangles.insert(new BasicTriangle(basicPoints), this);

        vertices = new Point[3];
        for (int i = 0; i < 3; i++) {
            vertices[i] = Point.getPoint(basicPoints[i]);
        }

        for (int i = 0; i < 3; i++) {
            vertices[i].neighborTriangles.add(this);
        }

        edges = new Edge[3];
        for (int i = 0; i < 3; i++) {
            edges[i] = Edge.getEdge(
                vertices[(i + 1) % 3],
                vertices[(i + 2) % 3]
            );
            component.merge(edges[i].component);
        }
        component.addTriangle(this);

        neighborTriangles = ArrayList.merge3Lists(
            (t1, t2) -> t1.creationTime - t2.creationTime,
            edges[0].neighborTriangles,
            edges[1].neighborTriangles,
            edges[2].neighborTriangles
        );

        neighborTriangles.forEach(triangle -> {
            triangle.neighborTriangles.add(this);
        });

        for (int i = 0; i < 3; i++) {
            edges[i].neighborTriangles.add(this);
            if (edges[i].neighborTriangles.size() == 1) {
                edges[i].boundaryNode = Edge.boundaryEdges.add(edges[i]);
            } else if (edges[i].neighborTriangles.size() == 2) {
                Edge.boundaryEdges.delete(edges[i].boundaryNode);
                edges[i].boundaryNode = null;
            }

            Edge.setTrianglesNeighborStats(edges[i].neighborTriangles.size());
        }
    }

    boolean checkValid(BasicPoint[] basicPoints) {
        BasicPoint p1 = basicPoints[0];
        BasicPoint p2 = basicPoints[1];
        BasicPoint p3 = basicPoints[2];
        double d1 = GeometryUtil.distance(p1, p2);
        double d2 = GeometryUtil.distance(p2, p3);
        double d3 = GeometryUtil.distance(p3, p1);

        if (Double.compare(d3, d1) >= 0) {
            if (Double.compare(d3, d2) >= 0) {
                return Double.compare(d3, d1 + d2) != 0;
            } else {
                return Double.compare(d2, d1 + d3) != 0;
            }
        } else {
            if (Double.compare(d1, d2) >= 0) {
                return Double.compare(d1, d2 + d3) != 0;
            } else {
                return Double.compare(d2, d1 + d3) != 0;
            }
        }
    }

    ArrayList<Triangle> extendedNeighborTriangles() {
        return ArrayList.merge3Lists(
            (t1, t2) -> t1.creationTime - t2.creationTime,
            vertices[0].neighborTriangles,
            vertices[1].neighborTriangles,
            vertices[2].neighborTriangles,
            this
        );
    }

    @Override
    public int compareTo(Triangle other) {
        int comp;
        if ((comp = vertices[0].compareTo(other.vertices[0])) != 0) {
            return comp;
        }

        if ((comp = vertices[1].compareTo(other.vertices[1])) != 0) {
            return comp;
        }

        if ((comp = vertices[2].compareTo(other.vertices[2])) != 0) {
            return comp;
        }

        return 0;
    }

    @Override
    public Point[] triangle_coord() {
        return vertices;
    }

    private void markUnvisited(Triangle[] triangles) {
        for (Triangle t : triangles) {
            t.visited = false;
        }
    }

    boolean visited = false;
    public int getDiameter(ConnectedComponent comp) {
        if (comp == null) {
            System.err.println("No connected component found");
            return 0;
        }
        Triangle[] triangles = new Triangle[comp.triangles().size()];
        comp.triangles().copyToArray(triangles);
        int maxDiameter = 0;
        for (Triangle triangle : triangles) {
            markUnvisited(triangles);
            LinkedList<Triangle> queue = new LinkedList<>();
            queue.add(triangle);
            int distance = -1;
            int numNodes = 1;
            while (numNodes != 0) {
                distance++;
                int newNodes = 0;
                while(numNodes-- > 0) {
                    ArrayList<Triangle> neighbors = queue.pop().neighborTriangles;
                    for (int i = 0; i < neighbors.size(); i++) {
                        Triangle neighbor = neighbors.get(i);
                        if (!neighbor.visited) {
                            neighbor.visited = true;
                            queue.add(neighbor);
                            newNodes++;
                        }
                    }
                }
                numNodes = newNodes;
            }
            if (distance > maxDiameter) {
                maxDiameter = distance;
            }
        }
        return maxDiameter;
    }

    public int getDiameter() {
        return getDiameter(ConnectedComponent.getComponent(component));
    }

    public static int maxDiameter() {
        ConnectedComponent comp = ConnectedComponent.maxTriangleComp();
        return comp.repTriangle.getDiameter(comp);
    }
}