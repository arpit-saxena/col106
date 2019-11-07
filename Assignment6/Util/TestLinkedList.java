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

    @Test
    public void testForEach() {
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i);
        }

        LinkedList<Integer> other = new LinkedList<>();
        list.forEach(value -> {
            other.add(value);
        });

        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4}, other.toArray());
    }

    @Test
    public void testAddLinkedList() {
        LinkedList<Integer> l1 = new LinkedList<>();
        LinkedList<Integer> l2 = new LinkedList<>();
        l1.add(1); l1.add(2); l1.add(3);
        l1.addLinkedList(l2);
        assertArrayEquals(new Integer[]{1, 2, 3}, l1.toArray());
        assertEquals(3, l1.size);
        l1.addLinkedList(null);
        assertEquals(3, l1.size);
        assertArrayEquals(new Integer[]{1, 2, 3}, l1.toArray());
        l2.add(4); 
        LinkedList<Integer>.Node tail = l2.add(5);
        LinkedList<Integer>.Node l1tail = l1.tail;
        l1.addLinkedList(l2);
        assertSame(l1.tail, tail);
        assertSame(l2.head.previous, l1tail);
        assertEquals(5, l1.size);
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5}, l1.toArray());

        LinkedList<Integer> l3 = new LinkedList<>();
        l3.addLinkedList(l1);
        assertSame(l1.head, l3.head);
        assertSame(l1.tail, l3.tail);
        assertEquals(5, l3.size);
    }

    @Test
    public void testPop() {
        LinkedList<Integer> l1 = new LinkedList<>();
        LinkedList<Integer>.Node n1 = l1.add(1),
                                 n2 = l1.add(2),
                                 n3 = l1.add(3);
        assertEquals(new Integer(1), l1.pop());
        assertSame(n3, l1.tail);
        assertSame(n2, l1.head);
        assertNull(l1.head.previous);
        assertArrayEquals(new Integer[]{2, 3}, l1.toArray());

        assertEquals(new Integer(2), l1.pop());
        assertSame(n3, l1.tail);
        assertSame(n3, l1.head);
        assertNull(l1.head.previous);
        assertArrayEquals(new Integer[]{3}, l1.toArray());

        assertEquals(new Integer(3), l1.pop());
        assertNull(l1.tail);
        assertNull(l1.head);
        assertArrayEquals(new Integer[]{}, l1.toArray());

        assertNull(l1.pop());
    }
}