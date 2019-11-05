import Util.ArrayList;

class WrongNumberArgsException extends IllegalArgumentException {
    public WrongNumberArgsException(String s) {super(s);}
}

class InvalidTriangleException extends IllegalArgumentException {
    public InvalidTriangleException(String s) {super(s);}
}

public class Triangle implements Comparable<Triangle> {
    Point[] vertices;
    Edge[] edges;
    int creationTime;

    ArrayList<Triangle> neighborTriangles = new ArrayList<>();
    ArrayList<Triangle> extendedNeighborTriangles = new ArrayList<>();
    private static int globalCounter = 0;

    public static void resetStatics() {
        globalCounter = 0;
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

        vertices = new Point[3];
        for (int i = 0; i < 3; i++) {
            vertices[i] = Point.getPoint(basicPoints[i]);
        }

        extendedNeighborTriangles = ArrayList.merge3Lists(
            vertices[0].neighborTriangles,
            vertices[1].neighborTriangles,
            vertices[2].neighborTriangles
        );
        extendedNeighborTriangles.forEach(triangle -> {
            triangle.extendedNeighborTriangles.add(this);
        });

        for (int i = 0; i < 3; i++) {
            vertices[i].neighborTriangles.add(this);
        }

        edges = new Edge[3];
        for (int i = 0; i < 3; i++) {
            edges[i] = Edge.getEdge(
                vertices[(i + 1) % 3],
                vertices[(i + 2) % 3]
            );
        }

        neighborTriangles = ArrayList.merge3Lists(
            edges[0].neighborTriangles,
            edges[1].neighborTriangles,
            edges[2].neighborTriangles
        );

        neighborTriangles.forEach(triangle -> {
            triangle.neighborTriangles.add(this);
        });

        for (int i = 0; i < 3; i++) {
            edges[i].neighborTriangles.add(this);
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

    @Override
    public int compareTo(Triangle other) {
        return creationTime - other.creationTime;
    }
}