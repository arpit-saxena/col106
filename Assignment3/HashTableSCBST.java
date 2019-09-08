public class HashTableSCBST<K extends Comparable<K>, T> implements MyHashTable_<K, T> {
    BinarySearchTree<K, T> table[];
    int hashTableSize;

    public int h1(String str) {
        return (int) HashFunctions.djb2(str, this.hashTableSize);
    }

    public HashTableSCBST(int tableSize) {
        table = new BinarySearchTree[tableSize];
        this.hashTableSize = tableSize;
    }

    public int insert(K key, T obj) {
        int index = h1(key.toString());
        if (table[index] == null) {
            table[index] = new BinarySearchTree<>();
        }

        return table[index].insert(key, obj);
    }

    public int update(K key, T obj) {
        int index = h1(key.toString());
        BinarySearchTree<K, T> bst = table[index];
        if (bst == null) return -1;
        return bst.update(key, obj);
    }

    public int delete(K key) {
        int index = h1(key.toString());
        BinarySearchTree<K, T> bst = table[index];
        if (bst == null) return -1;
        return bst.delete(key);
    }

    public boolean contains(K key) {
        int index = h1(key.toString());
        BinarySearchTree<K, T> bst = table[index];
        if (bst == null) return false;
        return bst.contains(key);
    }

    public T get(K key) throws NotFoundException {
        int index = h1(key.toString());
        BinarySearchTree<K, T> bst = table[index];
        if (bst == null) throw new NotFoundException();
        return bst.get(key);
    }

    public String address(K key) throws NotFoundException {
        int index = h1(key.toString()) % this.hashTableSize;
        BinarySearchTree<K, T> bst = table[index];
        if (bst == null) throw new NotFoundException();
        return Integer.toString(index) + '-' + bst.address(key);
    }
}