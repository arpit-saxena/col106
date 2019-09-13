Assignment 3, COL106, Semester 1 2019-20
Arpit Saxena, 2018MT10742

First, we report the time complexities of various functions in the two different implementations of the hash table.

*Assumption*:
    We are assuming that the hash functions take a constant time for each input. While this is strictly not true as their time depends on the length of the string we are passing to them, we can assume that the keys are not too long i.e. they are always less than some constant size. This assumption makes our hash functions work in O(1).

1. Double Hashing
    - Insert:
        We observe that here hashes of the key are calculated using both hash functions and then the table is checked for an empty slot using the normal double hashing approach. The worst case comes when we have to probe all the slots that are currently filled before finding an unfilled slot. So, the worst case time complexity for insertion is O(n), where n is the number of elements in the table. While this is not likely to happen on random data (see (*)), it can happen when an adversary deliberately gives adverse inputs.
        Expected time = O(1 / (1 - lambda)), where lambda is the load factor. (From Introduction to Algorithms, Cormen et al.) Suppose n elements are to be inserted, we know that table size is 1.5n, so lambda < n / 1.5n => lambda < 2 / 3
        => 1 - lambda > 1 / 3
        => 1 / (1 - lambda) < 3 
        => Expected time = O(1)

    - Finding an element:
        This works by checking the slots in the table similar to how is done in insert, except for the fact that it only checks till the nodes indicate that a key might be inserted later in the table. Going by the adversary argument, we might get a query to find an element in which we have to probe all the slots that are currently filled before declaring that the element is not in the table. So, the worst case time for finding an element in the hash table is O(n), where n is the number of elements in the table. Expected time = O(1) (shown in insert point)

    - Update:
        Here, we want to find an element with the given key and then update it's value. Finding the element takes O(n) time, and updating it then requires a constant amount of time. So, update takes O(n) time in the worst case. Expected time = O(1)
    
    - Delete:
        Here too, we want to find an element with the given key and then delete it from the table. Finding the element takes O(n) time, and deleting in then requires constant time. So, delete takes O(n) time in the worst case. Expected time is O(1)

    - Contains, get, address:
        These simply find an element in the hash table, which we have found takes O(n) time in the worst case. So, these functions take O(n) time in the worst case. Expected time is O(1)
    
2. Separate Chaining with Binary Search Trees
    Here, we first describe the time complexities of various operations on binary search trees. This will make it easier to describe the complexity of various operations on the hash table in this approach.

    --- Binary Search Tree ---
        We are assuming the compareTo method of key works in O(1) time. This is a reasonable assumption if we assume keys are always less than a particular constant size.

        - insert:
            We start at the root, and compare a node's key to the key we want to insert to traverse the tree along the correct direction. We stop this process we reach a leaf, perform an insertion there and exit. A leaf can be at a maximum depth of h from the root, where h is height of the binary search tree. Since we always do a constant amount of work at each node, and visit h nodes, the time taken to insert is O(h) (this bound is not tight as a leaf might be just at a depth of 1 from the root. This is possible since we are not doing any balancing.) 

            In the worst case, the tree might degrade to a linked list in which case the height of the tree with n elements would be n-1. So, the worst case time for insertion would be O(n).

            Since average height of a BST with n nodes is O(log(n)), the expected time to insert is O(log(n)) (Expected height taken from https://cs.stackexchange.com/a/96451)

        - Finding a key in the tree:
            We traverse the tree similar to what was described previously. If the key is found at a particular place, the function returns or if we have reached a leaf node, we are certain the key does not exist in the tree. In the worst case, we traverse to a leaf which is at depth h. So, the time taken is O(h). Similar to previous case, due to adversarial inserts, our tree might have degenerated to a linked list, in which case h = n - 1 and the time taken would thus be O(n). Expected time = O(log n)

        - update, contains, get, address:
            They basically involve just finding a key in the tree and only differ in a constant number of operations being performed at each node and after the complete traversal. Thus, each of these method take O(h) time which is O(n) in the worst case. Expected time = O(log n)

        - delete:
            To delete a particular node, we first search for the node with the particular key. If not found, we return. We then try to find the successor node of the node we have to delete. To do this, we go to the leftmost element of the right subtree of the node we want to delete. This node, which we call the replacement node, is then placed in the deleted node's place. The replacement node's parent's child then point to the original right child of the replacement node. 

            We note that in each step, we go down a level in the tree. The replacement is done in constant time. So, the time taken for delete is O(h), where h is the height of the tree. In the worst case, this is O(n). Expected time = O(log n)

    --- Hash table operations ---

    - insert:
        We find the index of the key using the hash function and insert it in the associated binary tree. With adversarial input, all keys hash to the same index. So, we have to insert a key in a BST with n nodes, which takes O(n) time in the worst case.
        Thus, insert in this hash table takes O(n) time in the worst case.
        Since each node has lambda elements on average, and we need to insert in BST with lambda nodes, expected time complexity = O(log(lambda + 1)) (lambda + 1 as lambda is less than 1 and it's log would be negative)

    - Delete:
        We find the index for the key using the hash function, and delete the key from the associated BST. This takes O(n) time in the worst case. Expected time complexity = O(log(lambda + 1))

    - Update, contains, get, address:
        We first find the index for the key using the hash function, and then call the corresponding method of the BST. In each case, this takes O(n) time in the worst case. Expected time complexity = O(log(lambda + 1))

-----------------------------------------------------------------------------------------

An interesting observation:

(*) Inserting large number of items in the Double Hashing Hash Table:
    To generate a large test case, I downloaded a file with first names from the web and used it to generate first name, last name pairs. Basically, I took a name as a first name and generated full names by taking all others as the last name. Limiting the size of the original names to 300, I generated a test file of 90000 (= 300^2) insertions. It can be seen at https://drive.google.com/file/d/1pXcJJR23YINXgtPPX73OPGF44IR6mZfw/view?usp=sharing

    The hash table size used was 134999, which is a prime close to 1.5 times the number of items to be inserted. The resulting output which contained info regarding the number of times a new hash was calculated for an index was aggregated. This was done using the command:
        java Assignment3 134999 DH input.txt | sort -rn | uniq -c
    The output (with additional column headings) is:
    ---------------------------
    Number of   | Times a hash 
    occurrences | is calculated
    ----------------------------
              1 | 20
              4 | 17
              5 | 16
             10 | 15
             18 | 14
             26 | 13
             35 | 12
             63 | 11
             97 | 10
            170 | 9
            291 | 8
            505 | 7
            870 | 6
           1632 | 5
           3371 | 4
           7251 | 3
          15738 | 2
          59913 | 1

    This data is interesting since we see that at max, a hash was calculated only 20 times. Also, for 84% of the inserts, a hash was calculated at most 2 times. This shows that the performance for non-adversarial inputs is far better than the worst case.

(**) A minor point: Since we are using a non self-balancing binary search tree, it is quite easy to generate an input in which the trees degrade to linked lists yielding the worst case performance. We cannot generate a similar kind of worse case input in case of double hashing easily from any given set of inputs. We can, of course, choose inputs that give the same hashes for both the functions but this is not possible to do if the things we have to insert in the hash table are fixed.
