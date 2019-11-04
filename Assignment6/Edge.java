public class Edge implements EdgeInterface {
    Point[] endPoints;

    public Edge(Point p1, Point p2) {
        if (p1.compareTo(p2) <= 0) {
            endPoints = new Point[]{p1, p2};
        } else {
            endPoints = new Point[]{p2, p1};
        }
    }

    public PointInterface[] edgeEndPoints() {
        return new PointInterface[]{endPoints[0], endPoints[1]};
    }
}