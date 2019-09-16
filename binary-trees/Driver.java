public class Driver {
    public static void main(String[] args) {
        AVLTree bt = new AVLTree();
        bt.insert(1);
        bt.insert(2);
        bt.insert(4);
        bt.insert(5);
        bt.insert(6);
        bt.insert(7);
        bt.insert(8);
        bt.insert(9);
        bt.insert(10);
        bt.insert(11);
        bt.insert(12);
        bt.delete(1);
        bt.delete(4);
        bt.delete(2);
        bt.print();
    }
}