Assignment 5, COL106, Semester 1 2019-20
Arpit Saxena, 2018MT10742

First, the time complexities of all implemented data structures (and their operations) are reported along with a short justification for each:

1. TRIE
    For the insert, delete, search and startsWith, implementation of Trie calls a suitable method of TrieNode (which is recursive).
    Note: In time complexity analysis, l is the length of the string in question
    
    - insert: O(l)
        At each node, a character at a particular index of the word is added (which involves iterating over an ArrayList and inserting, but since the size of ArrayList is bounded above by 95, it is O(1)) and the insert is called on a particular child with index increased by 1. This continues till index becomes equal to length of the string to insert i.e. l. 
        The initial call is with index 0 and terminates at index l resulting in l + 1 calls of the insert method, resulting in O(l) time complexity.
    
    - search, startsWith: O(l)
        Both these methods call the findPrefixInChildren method of TrieNode, so their complexity will be equal to it's complexity.
        Similar to insert, index keeps getting increased in each function call till it becomes equal to length of the word (or prefix) resulting in l + 1 function calls, i.e. O(l) time complexity, l being the length of the word / prefix.

    - delete: O(l)
        Similar to previous, index is increased in each function call, terminating when index becomes equal to l (length of word to be deleted) resulting in O(l) time complexity.

    - printTrie: O(nl)   -- Read below for meaning of n and l
        Here, we have to print all the values stored in the trie rooted at the given node. For this, we need to hit upon each node. We then do a constant amount of work at each node (iterating through the sorted ArrayList containing the children of this node), so the complexity depends upon how many nodes we have in the trie. Suppose there are n l-length strings in that trie, and in the worst case they don't have a common prefix, so the complexity would be O(nl)

    - printLevel, print: O(95 ^ h)
        Both these methods call a common helper print command, which either prints the specific level it receives in the arguments or prints all if it gets -1.
        Here, we are maintaining a queue, removing elements from it, adding their children back and doing some processing on the node. The processing of each node takes constant time, and we need to process all nodes till a particular level (or till end). 
        Also, we need to process all nodes up to that particular level (or of all levels). Suppose each node can have maximum "a" possible children, and in the worst case each node will have all children. So, nodes at a level l are (root being at level 0) a^l. 
        Therefore, nodes till level h are
            a^0 + a^1 + ... + a^h
            = (a^(h+1) - 1) / (a - 1) 
            = O(a^(h+1))
        Therefore, time taken here is O(a^(h+1)). Since each node can have 95 possible children (ASCII values 32 - 126), a = 95.
        So, time taken is O(95 ^ h).


2. RED BLACK TREE
    Only implements the insert and search methods (as required by the specs) and these are made by calling appropriate methods of RedBlackNode
    Note: In the time complexity analysis that follows, n is the number of nodes in the red black tree, or equivalently, the number of distinct keys inserted in it.

    - insert: O(log(n))
        Firstly, we perform normal Binary Search tree insertion by calling insert method of root node, which works in O(h). Since this is a red black tree, so h = O(log n), which means that initial insertion works in O(log(n))
        Then, we check for potential violations of the red black tree properties and fix them. For fixup, either we have determined that no possible violations are there in the tree, in which case we are done. Or we do a recoloring, which is a constant time operation, and call fixUp method of it's grandfather. Else we call rotate on this node, which is also a constant time operation, and we are done.
        So, we are moving up the tree two levels at a time performing constant operations at each level, and terminate when we reach the node, the time required to do this is O(h) = O(log(n)), due to this being a binary tree

    - search: O(log(n))
        This is a normal search of a Binary Search Tree, which works in O(h). Due to this being a Red Black Tree, h = O(log n) => Time complexity of search is O(log(n))

3. PRIORITY QUEUE
    Priority queue is implemented as a max heap. Internally, it stores things as nodes which have the value along with a timestamp to ensure FIFO for items with same priority. The modified priority then compares the given priority, and if equal, checks their timestamp to determine which one has higher priority. 
    Note: In the following analysis, n refers to the number of elements in the heap.

    - insert: O(log(n))
        In the left complete heap, new element is added at the end and then bubbled up according to it's priority. Bubbling up involves comparing it's priority with it's parent's priority and if higher interchanging them. So, we have a constant time operation at each level, going possibly all the way to the root, so time complexity is O(log(n)), where height of the tree is O(log(n)) since it is left complete

    - extractMax: O(log(n))
        This returns the root of the tree, and places the last element in the heap (last level, right most) at the root, and bubbles it down to maintain the heap property again. Bubbling down involves comparing the priority with both it's children, and if any of the children has a higher priority, interchanging it with the highest priority child, and continuing. So, a constant time operation happens at each level, possibly going to the last level, so time taken is O(h) and since this tree is left-complete, h = log(n) => Time complexity is O(log(n))

4.  HEAPish DATA STRUCTURE (using Red Black Tree)
    This structure wasn't required by the assignment specs but making it provides certain advantages as well as disadvantages, which are listed here briefly. Main disadvantage is that it can't be constructed in O(n) time as we would a max heap. And also that since we can't store it in a contiguous manner, we don't get the benefit of caching. Plus we haven't implemented delete in RB Tree, so no extract max is possible.
    The advantage, however is that we can easily query this structure for all elements greater than equal to a certain element, and also return certain number of largest elements very efficiently.
    Note: In the following analysis, n refers to number of nodes in the structure

    - topNumElements: O(log(n) + k)
        We do a sort of reverse order traversal of the tree here, and exit after hitting k nodes. So, we first go the rightmost node in log(n) steps and then it only takes a constant number of steps to go the next k nodes.
        Since topNumElements actually returns the top k *values*, it is possible that those values are there in less than k nodes, so the bound is not strict.

    - elementsGreaterThanEqualTo: O(n) (worst case)
        This function returns all values of the keys greater than something, which is specified by a comparator given to the function. During traversing the tree, in order, we can make an optimisation to ignore the whole left subtree if the particular key is less than what we're using. In a similar fashion, we can insert the whole right subtree, if the particular key is >= the one we're using, and then go left.
        It is similar to just linearly searching through a list in the worst case, but can eliminate large amount of keys to look from in the average case.

5. PROJECT MANAGEMENT
    Time complexity analysis here is done by the command given in the input file

    - PROJECT, USER: O(l)
        Here, a project and user object are created and inserted in their particular tries. Time complexity for this is order of the length of the project / user names, which is given by l, so time taken is O(l)

    - JOB: O(l + log(n))
        First, we check for existence of the given project name and user name, which takes time order of the length of the strings given (or height of trie, whichever is shorter). If they both exist, we insert the job in it's trie which takes O(l) time. Then, it is inserted to the jobQueue, which is a maxHeap. This takes time O(log(n)), n being being the number of jobs already in queue. It is also inserted into an ArrayList of allJobs for both it's project and it's creator, which takes O(1) time.

    - QUERY: O(l)
        This searches for the string in the jobs trie, which takes O(l) time, l being length of string searched or height of tree, whichever is shorter.

    - EMPTY_LINE: O(n * log(n) + n * log(n') + log(m))
        This involves repeatedly taking a job out of the max heap, which takes O(log(n)) time, n being the number of jobs inserted in the heap, till we find one which we can execute. The jobs which can't be executed due to budget constraints are added to their project's notReadyJobs queue, which takes O(1) time. The job is also added to a heapish structure of RB Tree, which takes O(log n') time considering n' nodes in the structure. Since there are n jobs, and in the worst case, we need to take out all the jobs giving a total complexity of O(n * log(n) + n * log(n')). We also need to change the position of the user in the heap of all users since it's budget consumption has now changed, so we bubble it up in that heap, which takes log(m) more time.

    - ADD: O(l + m log(n) + n' log(n'))
        Firstly we search for the string in the projects trie which takes time O(l). On adding budget to a particular project, we need to add all of it's not ready jobs back to the jobQueue, suppose it already has n jobs and m more jobs needed to be added, the time would be 
            O(log(n)) + (log(n + 1)) + ... + O(log(n + m)) 
            = O((m + n) log (m + n) - n log(n)) 
            = O(m log(m + n) + n log(m / n + 1))
        Assuming m to be significantly less than n, the time expression is simplified to O(m log(n)). This assumption is mostly valid since n would have jobs from all projects while m is just the not ready jobs for one project. We also have to remove these jobs from the allNotReadyJobs heap (implemented by RB Tree). Suppose it has n' jobs. We have to linearly search through it, and add back jobs which were not pushed to jobQueue. This takes n' log(n') time. The project is also removed from a HashMap which takes expected O(1) time and total time remains O(m log(n))

    - Printing stats: O(n_com + n_unf * log(n_unf))
        We first take all jobs that have been completed, and print them out. Suppose they were n_com, so this takes O(n_com) time.
        Then we iterate over all projects with unfinished jobs, and iterate over their jobs, inserting them into a jobsNotDone heap. In effect, we add all the unfinished jobs to a heap. Suppose there are n_unf unfinished jobs, then constructing a heap would take O(n_unf * log(n_unf)) time. Then we take each job out of the heap and print them, which again takes O(n_unf * log(n_unf)) time.
        So, total time taken is O(n_com + n_unf * log(n_unf))

    - NEW_USER, NEW_PROJECT: O(l + log(n) + m)
        First we need to find User / Project corresponding to the name given. We search in a Trie and can find this in O(l) time, l being size of string we were searching for. Then we take the list of all jobs of that object, which is sorted by arrival time and find the range we want by using a modified binary search on the array. It takes O(log n) time, n being the number of elements in the jobs array. Then, we have to add all these elements into a new ArrayList. Suppose these are m, then we take additional O(m) time in copying these over.

    - NEW_PROJECT_USER: O(log(n) + log(n') + l + l')
        What we do here is essentially run NEW_USER and NEW_PROJECT queries for the given user and project. Then we linearly search through the minimum of the arrays returned. So time taken is O(log(n) + log(n') + l + l') where n is length of allJobs list of user, n' the corresponding list of project, l is length of name of user, l' is length of name of project.

    - NEW_PRIORITY: O(m + log(n) + n')
        Here we search through our heapish structure of RB Tree which contains all notReady jobs. Suppose it has n nodes, i.e. n distinct priority values of the jobs which are not ready, and returns a list of m jobs, then we take a time of O(m + log(n)). Then we add all jobs from jobQueue, for which we have to essentially search linearly through the jobQueue, taking O(n') time more.

    - NEW_TOP: O(top * log(n))
        In the users heap, we keep extracting the max node till we have extracted it top times. Suppose the heap has n elements, so time taken is O(top * log(n)). Then we have to add these back, which also takes O(top * log(n)) time or O(n) time, whichever is smaller.

    - NEW_FLUSH: O(n + k*log(k))
        Here we linearly search through the jobQueue adding all the jobs which can be flushed to another list. Suppose we have n jobs in jobQueue and k jobs to potentially flush, so this takes O(n) time. We make a heap out of the potentially flushable jobs, which takes O(k) time which is O(n). Then we continuously extractMax from this heap, flush it if we can. This takes O(k * log(k)) time. Then we have to make the jobQueue again, which takes O(n) time.

** An Interesting observation:

This is more of a thought experiment I did comparing different ways of dealing with the jobs that can't be executed due to budget constraints, analysing the trade-offs of using different methods

1. We can go the naive way and store all the unfinished jobs in a list. It would require O(1) to insert each job, which is good. However, on adding budget to a project, we'd need to find unfinished jobs for that project, which would require a linear search through the unfinished jobs list, taking linear time. We can surely do better.

2. Since storing all the unfinished jobs in just a single list makes life difficult when adding budget to a project, maybe we can partition that list to store all the jobs of a particular project together, or better yet, store them in separate lists, each associated with a project. This makes it very easy as we can simply add a list to the Project class for unfinished jobs and on adding budget, just push all these jobs of the project back to the queue.

3. Next problem is encountered when printing stats. There, we need to print all the jobs that were unfinished, and that too in priority order and FIFO if priority is same. This would be easy if we had stored all jobs in a single queue. Storing the unfinished jobs in their projects however means that we need to iterate over all projects. This is inefficient as the number of projects with unfinished jobs can be much smaller than the total number of projects.

3.1 So we somehow have to store the projects with unfinished jobs. We want to insert projects in there and remove projects when budget is added to them. So, we need efficient insertion and deletion. This could've been done with the Red Black tree implementation, had we implemented the delete. Due to lack of it, however, we are using Hash Tables for this. They have some advantage as the expected time for insertion and deletion is both O(1).

** This assignment really helped in thinking of time complexities of different structures properly. And not only that, we had to even take other effects into account since the practical time taken matters. It really helped us see things like caching come into play, enormously changing performance of LinkedList vs ArrayList. 