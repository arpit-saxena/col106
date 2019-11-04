package Util;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestRBTree {

    @Test
    public void testConstruction() {
        RBTree<Integer, Integer> tree = new RBTree<>();
        assertNull(tree.root);
        assertEquals(tree.size, 0);
    }

    @Test
    public void testInsertBasic() {
        RBTree<Integer, Integer> tree = new RBTree<>();
        tree.insert(0, 1);
        assertEquals(new Integer(0), tree.search(0).key);
        assertArrayEquals(new Integer[]{1}, tree.search(0).values.toArray());
        tree.insert(0, 2);
        assertArrayEquals(new Integer[]{1, 2}, tree.search(0).values.toArray());
    }
}