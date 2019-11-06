import Util.ArrayList;
import Util.RBTree;

public class ConnectedComponent {
    static int count = 0;
    static ConnectedComponent maxTriangleComp = null;
    static int maxTriangles = 0;
    static int creationCounter = 0;
    BasicPoint sumCoordinates = new BasicPoint(0.0f, 0.0f, 0.0f);
    int numNodes = 0;
    ConnectedComponent parent = null;
    boolean infoLatest = true;
    int numTriangles = 0;
    Triangle repTriangle = null;
    int creationTime;

    ConnectedComponent() {
        count++;
        creationTime = creationCounter++;
    }

    static void resetStatics() {
        count = 0;
        maxTriangleComp = null;
        maxTriangles = 0;
        creationCounter = 0;
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

    void merge(ConnectedComponent other) {
        ConnectedComponent comp1 = getComponent(this);
        ConnectedComponent comp2 = getComponent(other);
        if (comp1 == comp2) return;

        count--;
        comp2.parent = comp1;
        comp1.numTriangles += comp2.numTriangles;
        if (comp1.numTriangles >= maxTriangles) {
            maxTriangles = comp1.numTriangles;
            maxTriangleComp = this;
        }
        if (comp1.repTriangle == null) {
            comp1.repTriangle = comp2.repTriangle;
        }
        comp1.infoLatest = false;
    }

    public void addTriangle(Triangle triangle) {
        if (triangle == null) return;
        ConnectedComponent comp = getComponent(this);
        comp.infoLatest = false;
        if (repTriangle == null) comp.repTriangle = triangle;
        comp.numTriangles++;
        if (comp.numTriangles > maxTriangles) {
            maxTriangles = comp.numTriangles;
            maxTriangleComp =this;
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof ConnectedComponent)) return false;
        ConnectedComponent otherComp = (ConnectedComponent) other;
        return getComponent(this) == getComponent(otherComp);
    }
}