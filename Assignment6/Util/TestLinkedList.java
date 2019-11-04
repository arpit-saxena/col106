package Util;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestLinkedList {

    @Test
    public void testAdd() {
        LinkedList<Integer> list = new LinkedList<>();

        assertTrue(list.size() == 0);
        assertArrayEquals(new Integer[]{} ,list.toArray());

        list.add(1);
        assertTrue(list.size() == 1);
        assertArrayEquals(new Integer[]{1}, list.toArray());

        list.add(2);
        assertTrue(list.size() == 2);
        assertArrayEquals(new Integer[]{1, 2}, list.toArray());

        assertTrue(list.head.value == 1);
        assertNotNull(list.head.next);
        assertTrue(list.head.next.value == 2);
        assertSame(list.head, list.head.next.previous);
        assertSame(list.head.next, list.tail);
    }

    @Test
    public void testAddReturn() {
        LinkedList<Integer> list = new LinkedList<>();
        LinkedList<Integer>.Node node = list.add(1);

        assertSame(list.head, node);
        assertSame(list.tail, node);

        LinkedList<Integer>.Node node2 = list.add(2);

        assertSame(list.head, node);
        assertSame(list.tail, node2);
    }

    @Test
    public void testDelete() {
        LinkedList<Integer> list = new LinkedList<>();
        LinkedList<Integer>.Node node = list.add(1);
        list.delete(node);

        assertTrue(list.size() == 0);
        assertArrayEquals(new Integer[]{}, list.toArray());
        assertNull(list.head);
        assertNull(list.tail);

        node = list.add(1); 
        LinkedList<Integer>.Node node2 = list.add(2);
        list.delete(node);
        assertSame(list.head, node2);
        assertSame(list.tail, node2);
    }
}