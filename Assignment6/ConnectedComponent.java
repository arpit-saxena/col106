import Util.ArrayList;
import Util.ComparablePair;
import Util.LinkedList;
import Util.RBTree;

public class ConnectedComponent implements Comparable<ConnectedComponent> {
    static int count = 0;
    static ConnectedComponent maxTriangleComp = null;
    static int maxTriangles = 0;
    static int creationCounter = 0;
    static ArrayList<BasicPoint> allCentroids;
    static boolean isLatest = true;
    BasicPoint sumCoordinates = new BasicPoint(0.0f, 0.0f, 0.0f);
    int numNodes = 0;
    ConnectedComponent parent = null;
    boolean infoLatest = true;
    int numTriangles = 0;
    Triangle repTriangle = null;
    int creationTime;
    LinkedList<Triangle> triangles = new LinkedList<>();

    ConnectedComponent() {
        count++;
        creationTime = creationCounter++;
    }

    static void resetStatics() {
        count = 0;
        maxTriangleComp = null;
        maxTriangles = 0;
        creationCounter = 0;
        allCentroids = null;
        isLatest = true;
    }

    static ConnectedComponent getComponent(ConnectedComponent comp) {
        if (comp == null) return null;
        if (comp.parent == null) return comp;
        comp.parent = getComponent(comp.parent);
        return comp.parent;
    }

    static void updateCentroids() {
        RBTree<Integer, ConnectedComponent> comps = new RBTree<>();
        Point.allPoints.forEach(point -> {
            ArrayList<ConnectedComponent> pointComps = new ArrayList<>();
            point.components.forEach(comp -> {
                comp = ConnectedComponent.getComponent(comp);
                if (!pointComps.addIfNotExists(comp, (a, b) -> a.equals(b) ? 0 : 1)) {
                    return;
                };
                int compNum = comp.creationTime;
                RBTree.Node<Integer, ConnectedComponent> node 
                    = comps.search(compNum);
                if (node == null) {
                    comps.insert(compNum, comp);
                    comp.infoLatest = true;
                    comp.sumCoordinates = new BasicPoint(0.0f, 0.0f, 0.0f);
                    comp.numNodes = 0;
                }
                comp.sumCoordinates.x += point.x;
                comp.sumCoordinates.y += point.y;
                comp.sumCoordinates.z += point.z;
                comp.numNodes++;
            });
        });

        // TODO: Improve sorting
        RBTree<BasicPoint, BasicPoint> centroids = new RBTree<>();
        comps.forEach(comp -> {
            BasicPoint centroid = comp.centroid();
            centroids.insert(centroid, centroid);
        });
        
        allCentroids = new ArrayList<>(centroids.size());
        centroids.forEach(centroid -> {
            allCentroids.add(centroid);
        });
        isLatest = true;
    }

    public static BasicPoint[] allCentroids() {
        if (!isLatest) {
            updateCentroids();
        }
        BasicPoint[] arr = new BasicPoint[allCentroids.size()];
        allCentroids.copyToArray(arr);
        return arr;
    }

    BasicPoint centroid() {
        ConnectedComponent comp = getComponent(this);

        if (!comp.infoLatest) {
            updateCentroids();
        }

        int numNodes = comp.numNodes;
        BasicPoint sumCoordinates = comp.sumCoordinates;

        if (numNodes == 0) return sumCoordinates;

        return new BasicPoint(
            sumCoordinates.x / numNodes,
            sumCoordinates.y / numNodes,
            sumCoordinates.z / numNodes
        );
    }

    static ConnectedComponent maxTriangleComp() {
        return getComponent(maxTriangleComp);
    }

    int numNodes() {
        ConnectedComponent comp = getComponent(this);
        if (!comp.infoLatest) {
            updateCentroids();
        }
        return comp.numNodes;
    }

    Triangle repTriangle() {
        return getComponent(this).repTriangle;
    }

    int numTriangles() {
        return getComponent(this).numTriangles;
    }

    LinkedList<Triangle> triangles() {
        return getComponent(this).triangles;
    }

    void merge(ConnectedComponent other) {
        ConnectedComponent comp1 = getComponent(this);
        ConnectedComponent comp2 = getComponent(other);
        if (comp1 == comp2) return;

        count--;
        comp2.parent = comp1;
        comp1.numTriangles += comp2.numTriangles;
        comp1.triangles.addLinkedList(comp2.triangles);
        comp2.triangles = null;
        if (comp1.numTriangles >= maxTriangles) {
            maxTriangles = comp1.numTriangles;
            maxTriangleComp = this;
        }
        if (comp1.repTriangle == null) {
            comp1.repTriangle = comp2.repTriangle;
        }
        comp1.infoLatest = false;
        isLatest = false;
    }

    public void addTriangle(Triangle triangle) {
        if (triangle == null) return;
        ConnectedComponent comp = getComponent(this);
        comp.infoLatest = false;
        isLatest = false;
        if (repTriangle == null) comp.repTriangle = triangle;
        comp.numTriangles++;
        if (comp.numTriangles > maxTriangles) {
            maxTriangles = comp.numTriangles;
            maxTriangleComp =this;
        }
        comp.triangles.add(triangle);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof ConnectedComponent)) return false;
        ConnectedComponent otherComp = (ConnectedComponent) other;
        return getComponent(this) == getComponent(otherComp);
    }

    @Override
    public int compareTo(ConnectedComponent other) {
        if (other == null) return -1;
        ConnectedComponent comp1 = getComponent(this);
        ConnectedComponent comp2 = getComponent(other);
        return comp1.creationTime - comp2.creationTime;
    }

    static class Counter {
        int count = 0;
        public int increment() {
            return count++;
        }
    }

    private static ConnectedComponent getPointsComponent(Point p) {
        ConnectedComponent comp1 = p.components.get(0);
        for (int j = 1; j < p.components.size(); j++) {
            if (!p.components.get(j).equals(comp1)) {
                return null;
            }
        }
        p.components = new ArrayList<>();
        p.components.add(comp1);
        return comp1;
    }

    private static float calcDist(Point p1, Point p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) 
               + (p1.y - p2.y) * (p1.y - p2.y)
               + (p1.z - p2.z) * (p1.z - p2.z);
    }

    private static class Container<T> {
        T val;
    }

    public static ComparablePair<Point, Point> closestComponents() {
        ArrayList<ArrayList<Point>> compPoints = new ArrayList<ArrayList<Point>>(count);
        ArrayList<Point> points = new ArrayList<>(Point.allPoints.size());
        RBTree<ConnectedComponent, Integer> comps = new RBTree<>();
        Point.allPoints.forEach(p -> points.add(p));
        for (int i = 0; i < points.size(); i++) {
            Point p = points.get(i);
            ConnectedComponent comp = getPointsComponent(p);
            if (comp == null) {
                return new ComparablePair<>(p, p);
            }
            RBTree.Node<ConnectedComponent, Integer> node = comps.search(comp);
            if (node != null) {
                compPoints.get(node.getValue()).add(p);
            } else {
                comps.insert(comp, compPoints.size());
                ArrayList<Point> newCompPoints = new ArrayList<>();
                newCompPoints.add(p);
                compPoints.add(newCompPoints);
            }
        }

        Container<Float> minDist = new Container<>();
        minDist.val = Float.MAX_VALUE;
        ComparablePair<Point, Point> minPair = new ComparablePair<Point,Point>(null, null);
        
        for (int comp1Index = 0; comp1Index < compPoints.size(); comp1Index++) {
            for (int comp2Index = comp1Index + 1; comp2Index < compPoints.size(); comp2Index++) {
                ArrayList<Point> points1 = compPoints.get(comp1Index),
                                 points2 = compPoints.get(comp2Index);

                points1.forEach(p1 -> {
                    points2.forEach(p2 -> {
                        float dist = calcDist(p1, p2);
                        if (dist < minDist.val) {
                            minDist.val = dist;
                            minPair.first = p1;
                            minPair.second = p2;
                        }
                    });
                });
            }
        }

        return minPair;
    }
}