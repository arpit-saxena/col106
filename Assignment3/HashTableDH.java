class Node<K, T> {
    Pair<K, T> pair;
    boolean checkFurther = false; // check_further <=> Calculate indices further

    public Node(K key, T value) {
        pair = new Pair<>(key, value);
    }

    public K key() {
        return pair.first;
    }

    public T value() {
        return pair.second;
    }

    public void setValue(T value) {
        pair.second = value;
    }
}

public class HashTableDH<K, T> implements MyHashTable_<K, T>{
    Node<K, T> table[];
    int hashTableSize;

    public int h1(String str) {
        return (int) HashFunctions.djb2(str, this.hashTableSize);
    }

    public int h2(String str) {
        return (int) HashFunctions.sdbm(str, this.hashTableSize);
    }

    public HashTableDH(int tableSize) {
        table = new Node[tableSize];
        this.hashTableSize  = tableSize;
    }

    public int insert(K key, T obj) {
        int hash1 = h1(key.toString());
        int hash2 = h2(key.toString());

        int index = hash1;
        int count = 1; // Count for calculating the hash function
        while (count < hashTableSize && table[index] != null) { // If count reaches hashTableSize, table is full
            table[index].checkFurther = true;
            index = (hash1 + count * hash2) % hashTableSize; 
            count++;
        }

        if (count == hashTableSize) {
            System.err.println("Hash Table full");
            return -1;
            System.exit(-1);
        }

        table[index] = new Node<K, T>(key, obj);
        return count;
    }

    public int update(K key, T obj) {
        int hash1 = h1(key.toString());
        int hash2 = h2(key.toString());

        int index = hash1;
        int count = 1; // Count for calculating the hash function

        while (count < hashTableSize) {
            if (table[index] == null) {
                break;
            }

            if (table[index].pair != null && table[index].key().equals(key)) {
                table[index].setValue(obj);
                return count;
            }

            if (table[index].checkFurther) {
                index = (hash1 + count * hash2) % hashTableSize;
                count++;
            } else {
                System.err.println("Element not found");
                return -1;
            }
        }

        System.err.println("Element not found. Exceeded size of table");
        return -1;
    }

    public int delete(K key) {
        int hash1 = h1(key.toString());
        int hash2 = h2(key.toString());

        int index = hash1;

        int count = 1;

        while (count < hashTableSize) {
            if (
                table[index] == null ||
                table[index].pair == null && !table[index].checkFurther
            ){
                System.err.println("Element to be deleted not found in table");
                return count;
            }

            if (table[index].pair == null) { // => table[index].checkFurther
                index = (hash1 + count * hash2) % hashTableSize;
                count++;
                continue;
            }

            if (table[index].key().equals(key)) {
                table[index].pair = null;
                break;
            } else if (table[index].checkFurther) {
                index = (hash1 + count * hash2) % hashTableSize;
                count++;
                continue;
            } else {
                System.err.println("Element to be deleted not found in table");
                break;
            }
        }

        int ret = count;

        // Updating the previous checkFurther links
        // count == 1 would imply there's nothing previous to this
        // So we need to find previous till count == 2
        while (count > 1 && !table[index].checkFurther && table[index].pair == null) {
            index = (index - hash2 + hashTableSize) % hashTableSize;
            table[index].checkFurther = false;
            count--;
        }

        return ret;
    }

    public boolean contains(K key) {
        try {
            addressInteger(key);
        } catch (NotFoundException e) {
            return false;
        }

        return true;
    }

    public T get(K key) throws NotFoundException {
        return table[addressInteger(key)].value();
    }

    public String address(K key) throws NotFoundException {
        return Integer.toString(addressInteger(key));
    }

    private int addressInteger(K key) throws NotFoundException {
        int hash1 = h1(key.toString());
        int hash2 = h2(key.toString());

        int index = hash1;

        int count = 1;

        while (count < hashTableSize) {
            if (
                table[index] == null ||
                table[index].pair == null && !table[index].checkFurther
            ) {
                throw new NotFoundException();
            }

            if (table[index].pair != null && table[index].key().equals(key)) {
                return index;
            }

            if (table[index].checkFurther) {
                index = (hash1 + count * hash2) % hashTableSize;
                count++;
            } else {
                throw new NotFoundException();
            }
        }

        throw new NotFoundException();
    }
}