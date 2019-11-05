public class ConnectedComponent {
    static int count = 0;
    BasicPoint sumCoordinates = new BasicPoint(0.0f, 0.0f, 0.0f);
    int numNodes = 0;
    ConnectedComponent parent = null;

    ConnectedComponent() {
        count++;
    }

    static void resetStatics() {
        count = 0;
    }

    static ConnectedComponent getComponent(ConnectedComponent comp) {
        if (comp == null) return null;
        if (comp.parent == null) return comp;
        comp.parent = getComponent(comp.parent);
        return comp.parent;
    }

    BasicPoint median() {
        ConnectedComponent comp = getComponent(this);
        int numNodes = comp.numNodes;
        BasicPoint sumCoordinates = comp.sumCoordinates;

        if (numNodes == 0) return sumCoordinates;

        return new BasicPoint(
            sumCoordinates.x / numNodes,
            sumCoordinates.y / numNodes,
            sumCoordinates.z / numNodes
        );
    }

    void merge(ConnectedComponent other) {
        ConnectedComponent comp1 = getComponent(this);
        ConnectedComponent comp2 = getComponent(other);
        if (comp1 == comp2) return;

        count--;
        comp1.sumCoordinates.x += comp2.sumCoordinates.x;
        comp1.sumCoordinates.y += comp2.sumCoordinates.y;
        comp1.sumCoordinates.z += comp2.sumCoordinates.z;
        comp1.numNodes += comp2.numNodes;
        comp2.parent = comp1;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (!(other instanceof ConnectedComponent)) return false;
        ConnectedComponent otherComp = (ConnectedComponent) other;
        return getComponent(this) == getComponent(otherComp);
    }
}