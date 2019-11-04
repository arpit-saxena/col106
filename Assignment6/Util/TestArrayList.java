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
}