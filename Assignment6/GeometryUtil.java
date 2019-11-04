public class GeometryUtil {
    public static double distance(BasicPoint p1, BasicPoint p2) {
        return Math.sqrt(
            (p1.x - p2.x) * (p1.x - p2.x)
            + (p1.y - p2.y) * (p1.y - p2.y)
            + (p1.z - p2.z) * (p1.z - p2.z)
        );
    }
}