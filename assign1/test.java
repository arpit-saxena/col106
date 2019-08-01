import java.util.Iterator;
import interfaces.Position_;

public class test {
    public static void t1() {
        LinkedList<Integer> ll = new LinkedList<Integer>();
        ll.add(1);
        ll.add(2);
        ll.add(5);
        System.out.println("Count: " + ll.count());

        Iterator<Integer> iter = ll.elements();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println();

        Iterator<Position_<Integer>> it2 = ll.positions();
        while (it2.hasNext()) {
            System.out.println(it2.next().value());
        }
    }

    private static void printReverse(Iterator<String> iter) {
        String next;
        if (iter.hasNext()) {
            next = iter.next();
            printReverse(iter);
            System.out.println(next);
        }
    }

    public static void main(String[] args) {
        LinkedList<String> l = new LinkedList<>();
        l.add("A");
        l.add("B");
        l.add("C");
        l.add("C");
        l.add("C");
        l.add("C");
        l.add("C");
        printReverse(l.elements());
    }
}