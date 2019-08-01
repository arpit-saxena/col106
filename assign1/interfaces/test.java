public class test {
    public static void main(String[] args) {
        LinkedList<Integer> ll = new LinkedList<Integer>();
        ll.add(1);
        ll.add(2);
        System.out.println("Count: " + ll.count());

        for (int a : ll.elements()) {
            System.out.println(a);
        }
    }
}