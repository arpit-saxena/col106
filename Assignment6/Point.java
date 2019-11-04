import Util.RBTree;

public class Point extends BasicPoint {
    static RBTree<BasicPoint, Point> allPoints = new RBTree<>();

    public static Point getPoint(float x, float y, float z) {
        return getPoint(new BasicPoint(x, y, z));
    }

    public static Point getPoint(BasicPoint basicPoint) {
        RBTree.Node<BasicPoint, Point> node = allPoints.search(basicPoint);
        if (node == null) {
            Point point = new Point(basicPoint);
            return point;
        }

        return node.getValue();
    }

    // Called only when given point does not exist
    private Point(BasicPoint point) {
        super(point.x, point.y, point.z);
        allPoints.insert(point, this);
    }
}