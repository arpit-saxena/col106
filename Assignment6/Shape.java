import Util.ArrayList;
import Util.ComparablePair;

public class Shape implements ShapeInterface
{
    @Override
    public boolean ADD_TRIANGLE(float[] t) {
        BasicPoint p1 = new BasicPoint(t[0], t[1], t[2]);
        BasicPoint p2 = new BasicPoint(t[3], t[4], t[5]);
        BasicPoint p3 = new BasicPoint(t[6], t[7], t[8]);

        try {
            new Triangle(p1, p2, p3);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    @Override
    public int TYPE_MESH() {
        return Edge.typeMesh();
    }

    float calcLengthSquared(EdgeInterface e) {
        PointInterface p1 = e.edgeEndPoints()[0];
        PointInterface p2 = e.edgeEndPoints()[1];
        return (p1.getX() - p2.getX()) * (p1.getX() - p2.getX())
               + (p1.getY() - p2.getY()) * (p1.getY() - p2.getY())
               + (p1.getZ() - p2.getZ()) * (p1.getZ() - p2.getZ());
    }

    @Override
    public EdgeInterface[] BOUNDARY_EDGES() {
        int size = Edge.boundaryEdges.size();
        if (size == 0) return null;
        EdgeInterface[] boundary = new EdgeInterface[size];
        Edge.boundaryEdges.copyToArray(boundary);
        ArrayList.sortArray(boundary, (e1, e2) -> {
            float dist = calcLengthSquared(e1) - calcLengthSquared(e2);
            if (dist < 0.0f) return -1;
            else if (dist == 0.0f) return 0;
            return 1;
        });
        return boundary;
    }

    @Override
    public int COUNT_CONNECTED_COMPONENTS() {
        return ConnectedComponent.count;
    }

    private Triangle getTriangle(float[] t) {
        BasicPoint p1 = new BasicPoint(t[0], t[1], t[2]);
        BasicPoint p2 = new BasicPoint(t[3], t[4], t[5]);
        BasicPoint p3 = new BasicPoint(t[6], t[7], t[8]);

        return Triangle.getTriangle(p1, p2, p3);
    }

    @Override
    public TriangleInterface[] NEIGHBORS_OF_TRIANGLE(float[] t) {
        Triangle triangle = getTriangle(t);
        if (triangle == null) return null;
        ArrayList<Triangle> neighbors = triangle.neighborTriangles;
        TriangleInterface[] arr = new TriangleInterface[neighbors.size()];
        neighbors.copyToArray(arr);
        return arr;
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBOR_TRIANGLE(float[] t) {
        Triangle triangle = getTriangle(t);        
        if (triangle == null) return null;
        return triangle.edges;
    }

    @Override
    public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float[] t) {
        Triangle triangle = getTriangle(t);
        if (triangle == null) return null;
        return triangle.vertices;
    }

    @Override
    public TriangleInterface[] EXTENDED_NEIGHBOR_TRIANGLE(float[] t) {
        Triangle triangle = getTriangle(t);
        if (triangle == null) return null;
        ArrayList<Triangle> neighbors = triangle.extendedNeighborTriangles();
        if (neighbors.size() == 0) return null;
        TriangleInterface[] ret = new TriangleInterface[neighbors.size()];
        neighbors.copyToArray(ret);
        return ret;
    }

    @Override
    public TriangleInterface[] INCIDENT_TRIANGLES(float[] p) {
        Point point = Point.getPointIfExists(p);
        if (point == null) return null;
        ArrayList<Triangle> neighbors = point.neighborTriangles;
        TriangleInterface[] arr = new TriangleInterface[neighbors.size()];
        neighbors.copyToArray(arr);
        return arr;
    }

    @Override
    public PointInterface[] NEIGHBORS_OF_POINT(float[] p) {
        Point point = Point.getPointIfExists(p);
        if (point == null) return null;
        ArrayList<Point> neighbors = point.neighborPoints;
        PointInterface[] arr = new PointInterface[neighbors.size()];
        neighbors.copyToArray(arr);
        return arr;
    }

    @Override
    public EdgeInterface[] EDGE_NEIGHBORS_OF_POINT(float[] p) {
        Point point = Point.getPointIfExists(p);
        if (point == null) return null;
        ArrayList<Edge> neighbors = point.neighborEdges;
        EdgeInterface[] arr = new EdgeInterface[neighbors.size()];
        neighbors.copyToArray(arr);
        return arr;
    }

    @Override
    public TriangleInterface[] FACE_NEIGHBORS_OF_POINT(float[] p) {
        return INCIDENT_TRIANGLES(p);
    }

    @Override
    public boolean IS_CONNECTED(float[] t1, float[] t2) {
        Triangle triangle1 = getTriangle(t1);
        if (triangle1 == null) return false;

        Triangle triangle2 = getTriangle(t2);
        if (triangle2 == null) return false;

        return triangle1.component.equals(triangle2.component);
    }

    private Edge getEdge(float [] e) {
        Point p1 = Point.getPointIfExists(e[0], e[1], e[2]);
        if (p1 == null) return null;
        Point p2 = Point.getPointIfExists(e[3], e[4], e[5]);
        if (p2 == null) return null;
        return Edge.getEdgeIfExists(p1, p2);
    }

    @Override
    public TriangleInterface[] TRIANGLE_NEIGHBOR_OF_EDGE(float[] e) {
        Edge edge = getEdge(e);
        ArrayList<Triangle> neighbors = edge.neighborTriangles;
        TriangleInterface[] arr = new TriangleInterface[neighbors.size()];
        neighbors.copyToArray(arr);
        return arr;
    }

    @Override
    public PointInterface[] CENTROID() {
        return ConnectedComponent.allCentroids();
    }

    @Override
    public PointInterface CENTROID_OF_COMPONENT(float[] p) {
        Point point = Point.getPointIfExists(p);
        if (point == null) return null;
        return point.components.get(0).centroid();
    }

    @Override
    public int MAXIMUM_DIAMETER() {
        return Triangle.maxDiameter();
    }

    @Override
    public PointInterface[] CLOSEST_COMPONENTS() {
        ComparablePair<Point, Point> pair = ConnectedComponent.closestComponents();
        return new PointInterface[]{pair.first, pair.second};
    }
}