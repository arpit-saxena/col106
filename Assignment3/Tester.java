public class Tester {
    public static void main(String[] args) throws Exception {
        /* System.out.println(HashFunctions.djb2("KalluYadav", 5));
        System.out.println(HashFunctions.djb2("TilluYadav", 5));
        System.out.println(HashFunctions.djb2("ShyaamSingh", 5)); */
        
        /* System.out.println(HashFunctions.djb2("JanardanBhartiya", 5));
        System.out.println(HashFunctions.djb2("ShoukatIsmaail", 5));
        System.out.println(HashFunctions.djb2("JackNicholson", 5));
        System.out.println(HashFunctions.djb2("KalluYadav", 5));
        System.out.println(HashFunctions.djb2("TunniBai", 5));
        System.out.println(HashFunctions.djb2("TilluYadav", 5)); */
        
        BinarySearchTree<Integer, Integer> bst = new BinarySearchTree<>();
        System.out.println(bst.insert(8, 8));
        System.out.println(bst.insert(6, 6));
        System.out.println(bst.insert(10, 10));
        System.out.println(bst.insert(2, 2));
        System.out.println(bst.insert(7, 7));
        System.out.println(bst.insert(9, 9));
        System.out.println(bst.insert(20, 20));
        System.out.println(bst.insert(1, 1));
        System.out.println(bst.insert(4, 4));
        System.out.println(bst.insert(5, 5));
        System.out.println(bst.insert(15, 15));
        System.out.println(bst.insert(17, 17));
        System.out.println();
        System.out.println(bst.delete(10));
        System.out.println();
        System.out.println(bst.address(1));
        System.out.println(bst.address(2));
        System.out.println(bst.address(4));
        System.out.println(bst.address(5));
        System.out.println(bst.address(6));
        System.out.println(bst.address(7));
        System.out.println(bst.address(8));
        System.out.println(bst.address(9));
        System.out.println(bst.address(15));
        System.out.println(bst.address(17));
        System.out.println(bst.address(20));
    }
}