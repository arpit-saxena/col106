package Util;

import static org.junit.Assert.*;
import org.junit.Test;

import Util.ArrayList;

public class TestArrayList {

    @Test
    public void testConstruction() {
        ArrayList<Integer> a = new ArrayList<>();
        assertEquals(a.size(), 0);
    }

    @Test
    public void testAddOne() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(1);
        assertEquals(a.size(), 1);
        assertEquals(a.get(0), new Integer(1));
    }

    @Test
    public void testIllegalIndex() {
        ArrayList<Integer> a = new ArrayList<>();
        try {
            a.get(0);
        } catch(IndexOutOfBoundsException e) {
            a.add(1);
            try {
                a.get(1);
            } catch (IndexOutOfBoundsException e2) {
                return;
            } catch (Exception e2) {
                fail();
            }
        } catch (Exception e) {
            fail();
        }

        fail();
    }

    @Test
    public void testSize() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(0);
        assertEquals(a.size(), 1);
        a.add(2);
        assertEquals(a.size(), 2);
    }

    @Test
    public void testAddAll() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(-1);
        Integer[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        a.addAll(arr);
        assertEquals(a.size(), 11);
        for (int i = 1; i < 11; i++) {
            assertEquals(new Integer(i - 1), a.get(i));
        }
    }

    @Test
    public void testToArray() {
        ArrayList<Integer> a = new ArrayList<>();
        a.add(0);
        a.add(1);
        assertArrayEquals(new Integer[]{0, 1}, a.toArray());
        a.addAll(new Integer[]{2, 3, 4, 5});
        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5}, a.toArray());
    }

    @Test
    public void testZeroCapacity() {
        ArrayList<Integer> a = new ArrayList<>(0);
        a.add(0);
        a = new ArrayList<>(0);
        a.addAll(new Integer[]{1,2,3});
    }

    @Test
    public void testConsecutiveAdds() {
        ArrayList<Integer> a = new ArrayList<>();
        Integer[] ins = new Integer[100];
        for (int i = 0; i < 100; i++) {
            ins[i] = i;
            a.add(i);
        }

        for(int i = 0; i < 100; i++) {
            assertEquals(new Integer(i), a.get(i));
        }

        assertArrayEquals(ins, a.toArray());
    }

    @Test
    public void testMergeLists() {
        ArrayList<Integer> a = new ArrayList<>();
        ArrayList<Integer> b = new ArrayList<>();
        Comparator<Integer> comp = (x, y) -> x - y;
        
        a.add(1); a.add(2);
        b.add(3);

        assertArrayEquals(
            new Integer[]{1, 2, 3},
            ArrayList.merge2Lists(comp, a, b).toArray()
        );
        assertArrayEquals(
            new Integer[]{1, 2, 3},
            ArrayList.merge2Lists(comp, b, a).toArray()
        );

        a.add(3);
        assertArrayEquals(
            new Integer[]{1, 2, 3},
            ArrayList.merge2Lists(comp, a, b).toArray()
        );

        a.add(4); a.add(5);
        b.add(5);
        assertArrayEquals(
            new Integer[]{1, 2, 3, 4, 5},
            ArrayList.merge2Lists(comp, a, b).toArray()
        );

        ArrayList<Integer> c = new ArrayList<>();
        c.add(4); c.add(5); c.add(6);
        assertArrayEquals(
            new Integer[]{1, 2, 3, 4, 5, 6},
            ArrayList.merge3Lists(comp, a, b, c).toArray()
        );

        a.add(10); a.add(11);
        b.add(11);
        assertArrayEquals(
            new Integer[]{1, 2, 3, 4, 5, 6, 10, 11},
            ArrayList.merge3Lists(comp, a, b, c).toArray()
        );

        a = new ArrayList<>();
        assertArrayEquals(
            b.toArray(), 
            ArrayList.merge2Lists(comp, a, b).toArray()
        );
        assertArrayEquals(
            b.toArray(), 
            ArrayList.merge2Lists(comp, b, a).toArray()
        );
    }

    @Test
    public void testForEach() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        ArrayList<Integer> other = new ArrayList<>();
        list.forEach(value -> {
            other.add(value);
        });

        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4}, other.toArray());
    }

    @Test
    public void testBubbleSort() {
        assertArrayEquals(
            new Integer[]{}, 
            ArrayList.bubbleSort(new Integer[]{})
        );
        assertArrayEquals(
            new Integer[]{1}, 
            ArrayList.bubbleSort(new Integer[]{1})
        );
        assertArrayEquals(
            new Integer[]{1, 2}, 
            ArrayList.bubbleSort(new Integer[]{1, 2})
        );
        assertArrayEquals(
            new Integer[]{1, 2}, 
            ArrayList.bubbleSort(new Integer[]{2, 1})
        );
        assertArrayEquals(
            new Integer[]{1, 2, 3}, 
            ArrayList.bubbleSort(new Integer[]{3, 2, 1})
        );
        assertArrayEquals(
            new Integer[]{1, 2, 3, 4}, 
            ArrayList.bubbleSort(new Integer[]{3, 4, 2, 1})
        );
        assertArrayEquals(
            new Integer[]{1, 2, 3, 4}, 
            ArrayList.bubbleSort(new Integer[]{4, 3, 2, 1})
        );
    }

    @Test
    public void testSearch() {
        Comparator<Integer> comparator = (a, b) -> a - b;
        ArrayList<Integer> list = new ArrayList<>();
        list.addAll(new Integer[]{0, 1, 2, 3, 4, 5, 6});
        for (int i = 0; i <= 6; i++) {
            assertTrue(list.binarySearch(i, comparator));
            assertTrue(list.linearSearch(i, comparator));
        }
        assertFalse(list.binarySearch(-1, comparator));
        assertFalse(list.binarySearch(7, comparator));
        assertFalse(list.linearSearch(-1, comparator));
        assertFalse(list.linearSearch(7, comparator));
    }

    @Test
    public void testAddIfNotExists() {
        Comparator<Integer> comparator = (a, b) -> a - b;
        ArrayList<Integer> list = new ArrayList<>();
        list.addAll(new Integer[]{1, 2, 3});
        assertFalse(list.addIfNotExists(3, comparator));
        assertArrayEquals(new Integer[]{1, 2, 3}, list.toArray());

        assertFalse(list.addIfNotExists(2, comparator));
        assertArrayEquals(new Integer[]{1, 2, 3}, list.toArray());

        assertTrue(list.addIfNotExists(0, comparator));
        assertArrayEquals(new Integer[]{1, 2, 3, 0}, list.toArray());

        assertTrue(list.addIfNotExists(4, comparator));
        assertArrayEquals(new Integer[]{1, 2, 3, 0, 4}, list.toArray());
    }

    class TestClass {
        int a = 0;
    }

    @Test
    public void testClassCast() {
        ArrayList<TestClass> list = new ArrayList<>();
        list.add(new TestClass());
        TestClass[] a = new TestClass[list.size()];
        list.copyToArray(a);
        assertEquals(0, a[0].a);
    }

    @Test
    public void testCopyToArray() {
        
    }
}