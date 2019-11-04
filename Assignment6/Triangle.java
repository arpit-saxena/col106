class WrongNumberArgsException extends IllegalArgumentException {
    public WrongNumberArgsException(String s) {super(s);}
}

class InvalidTriangleException extends IllegalArgumentException {
    public InvalidTriangleException(String s) {super(s);}
}

public class Triangle {
    Point[] vertices = new Point[3];
    Edge[] edges = new Edge[3];

    public Triangle(BasicPoint... basicPoints) {
        if (basicPoints.length != 3) {
            throw new WrongNumberArgsException(
                "Expected 3 points, got " + basicPoints.length
            );
        } else if (!checkValid(basicPoints)) {
            throw new InvalidTriangleException(
                "Given points don't form a valid triangle"
            );
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
}