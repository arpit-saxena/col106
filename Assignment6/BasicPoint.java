/**
 * BasicPoint is a simple implementation of the point interface
 * without having additional semantics of a point, like neighboring
 * triangles, etc.
 */
public class BasicPoint implements PointInterface, Comparable<BasicPoint> {
    float x;
    float y;
    float z;

    public BasicPoint(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getZ() {
        return z;
    }

    @Override
    public float [] getXYZcoordinate() {
        return new float[]{x, y, z};
    }

    @Override
    public int compareTo(BasicPoint other) {
        int result;
        if ((result = Float.compare(x, other.x)) != 0) {
            return result;
        }

        if ((result = Float.compare(y, other.y)) != 0) {
            return result;
        }

        if ((result = Float.compare(z, other.z)) != 0) {
            return result;
        }

        return 0;
    }
}