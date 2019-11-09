Assignment 6, COL106, Semester 1 2019-20
Arpit Saxena, 2018MT10742

Some data structures that were not very intricately tied with this assignment have been implemented in a separate folder (and thus package) named Util, and them imported whenever required. These structures' working and time complexities of some functions are first explained:

1. ARRAY LIST
    A resizable array implementation along similar lines to java.util.ArrayList.
    capacity of an ArrayList means the length of the internal array and size of an ArrayList is the number of elements stored in it.

    - constructor:
        Takes in a capacity, takes capacity as 10 if not given already. Sets the size as 0.

    - expandCapacity: O(size)
        Given the new capacity, allocates a new array of that size, copies over the old elements to the new array, and then sets this array as the internal array of the structure/

    - add, addAll: O(1) amortized (time to insert 1 value)
        These functions append the given value/array of values to the list. If capacity is less than what is required, the capacity is expanded by calling expandCapacity.

    - forEach: O(size)
        Iterates over the internal array and calls consumer on each value;
    
    - linearSearch: O(size) worst case
        Searches for a given value in the array using the comparator provided, and returns true if any element is equal to the value we are looking for (according to the comparator of course)

    - binarySearch: O(log(size))
        Assumes that comparator defines a total order and the elements of the internal array are sorted according to it. Then does a binary search over the elements.

    - search:
        Tries to take into account constants involved in binary search, and does linear search when size is below 10 and binary search otherwise to get an optimal search;

    - addIfNotExists:
        Linearly searches through the array and if the value does not exist, appends it.

    - merge2Lists, merge3Lists: O(n)
        Basically merges the given lists, assuming they are sorted by the given comparator, but only adds the repeated elements once. 

    - sorts:
        - quickSort: O(n log(n))
            Implements the quicksort by taking a random pivot (using System.nanoTime() and modulo) and then creating three partitions, with less than, equal to and greater than the separator value. Then the sorting is done on the first and third partitions.

        - insertionSort: O(n^2)
            Standard insertion sort
        
        Sorting is done by calling quickSort and then switching to insertion sort when the size is below a particular threshold, which is currently 8.

2. LINKED LIST
    Doubly linked list implementation with references to head and tail of the list.
    Implements standard functions for a linked list.

3. COMPARABLE PAIR, COMPARABLE TRIPLE, COMPARATOR
    Implements a comparable pair and triple for general use cases. Also a general use Comparator interface to be used.

3. RED BLACK TREE
    Only implements the insert and search methods and these are made by calling appropriate methods of RedBlackNode
    Note: In the time complexity analysis that follows, n is the number of nodes in the red black tree, or equivalently, the number of distinct keys inserted in it.

    - insert: O(log(n))
        Firstly, we perform normal Binary Search tree insertion by calling insert method of root node, which works in O(h). Since this is a red black tree, so h = O(log n), which means that initial insertion works in O(log(n))
        Then, we check for potential violations of the red black tree properties and fix them. For fixup, either we have determined that no possible violations are there in the tree, in which case we are done. Or we do a recoloring, which is a constant time operation, and call fixUp method of it's grandfather. Else we call rotate on this node, which is also a constant time operation, and we are done.
        So, we are moving up the tree two levels at a time performing constant operations at each level, and terminate when we reach the node, the time required to do this is O(h) = O(log(n)), due to this being a binary tree

    - search: O(log(n))
        This is a normal search of a Binary Search Tree, which works in O(h). Due to this being a Red Black Tree, h = O(log n) => Time complexity of search is O(log(n))

    - forEach: O(m), m is the total number of values stored
        Takes in a consumer. Performs an in-order traversal and calls the consumer for each value in the node.

Now, we mention the structures and methods of the various classes implemented:

1. BasicPoint
    This implements PointInterface and Comparable.

2. Point EXTENDS BasicPoint
    Statically stores all points in an RBTree with BasicPoint as key type and Point as values.

    - static getPoint(): O(log(n)), n is number of points
        Gets a point, given it's coordinates. First searches in the RBTree, and if found returns that. Else constructs a new point, inserts it to the allPoints tree and then returns that.

    - static getPointIfExists(): O(log(n)), n is number of points
        Similar to previous, but does not construct a new point.

    - Stores the list of its neighbor points, edges, triangles and the list of ConnectedComponents it belongs to.

3. Edge
    This implements EdgeInterface.

    - getEdge(), getEdgeIfExists():
        Similar to getPoint() and getPointIfExists()

    - Constructor: O(log(n)), n is number of edges
        Inserts the edge into the allEdges RBTree, and inserts this edge to the neighborEdges list of both points. Note that the pair of these points is sorted in increasing order. Also adds this nodes component to the component of it's endpoints, if they don't already have that.

    - Statically stores all the boundaryEdges in a LinkedList, all edges in a RBTree. Stores the lists of all neighbor triangles and it's ConnectedComponent.

4. BasicTriangle
    This extends ComparableTriple and we can see it's basically a ComparableTriple of BasicPoints with those points sorted.

5. Triangle
    This implemented TriangleInterface and Comparable.

    - getTriangle(): O(log(n)), n is total number of triangles in shape
        Searches for a triangle in a RBTree of allTriangles which is keyed with BasicTriangle and has Triangle as values. This takes log(n) time. If the triangle is found it is returned, else null is returned.

    - checkValid: O(1)
        This is based on checking the triangle inequality. If sum of lengths of two edges is the same as that of the third edge, then it is not a valid triangle. Else it is valid.
        
    - Constructor: O(nt + log(np) + log(ne))
        Checks validity of the triangle, throws InvalidTriangleException if invalid. Inserts this triangle into the allTriangles RBTree, which takes O(log(nt)) time, nt being the number of triangles. Gets the points and edges by the getPoint and getEdge methods respectively, which take O(log(np)) and O(log(ne)) time, np and ne being the number of points and edges respectively. The components of edges are merged which takes O(1) time. This triangle is then added to it's component's list, which takes O(1) time. The list of all it's neighbors is constructed by merging the list of neighbor triangles of all it's edges which takes O(nt) time, as number of neighbors of it can be nt in the worst case. For each of these neighboring triangles, this triangle is added to their neighborTriangles list. This takes O(nt) time. Then for each of it's edges, we check to see if it's a boundary edge or not and update the boundaryEdges list. This takes O(1) time. 

    - extendedNeighborTriangles(): O(n)
        This merges the neighbor triangle list of all of it's vertices, but not keeping the duplicates. This takes O(n) time, n being the number of extended neighbors of the triangle.

    - getDiameter: O(n * (m + n)) = O(n ^ 3)
        Given a ConnectedComponent whose diameter we want to find, first we store all it's triangles in an array and mark them unvisited. Then for all rectangles as starting rectangle, we do a breadth first traversal, keeping track of the distance. The distance of the node processed at the end. Let n be the total number of triangles in the component and m be the total number of neighbors of a triangle i.e. the number of edges in the graph. So time taken is O(m + n)

    - maxDiameter: O(m + n)
        We first take the component with the maximum diameter which takes O(1) time (as we have it stored) and then find it's diameter.

6. ConnectedComponent
    Since a list of triangles belonging to a component is disjoint to the triangles belonging to the other component, we can use the union-find data structure to have almost constant merges and finds. It's actually upper bounded by the inverse Ackermann function (read on Wikipedia) which is a really slow growing function, so we just mention it as O(1). 

    - merge: O(1)
        Takes in a component, and merges that into itself. Makes this component tha parent of the other component. Also does other O(1) work to maintain list of triangles, etc.

    - addTriangle: O(1)
        Adds a triangle to this ConnectedComponent. Adds this to the linked list in O(1) time, and does some more processing in O(1).

    - getPointsComponent: O(n)
        A bit of a problem with this approach is that merging two components to which a point belongs renders the components stored in an array in it as same. This is not easy to fix as we'll have to remove one of them from the array, So, in this implementation, we just let the array be, and then to find the component of a point we can just take the first element of this list. But if we have to determine how many components it belongs to, then we have to traverse the list checking if two are same or not. This is done in this function which returns the component if the point only belongs to one component, else null.

    - static closestComponents: O(n ^ 2), n being the number of vertices
        We traverse the list of all points. For a given point, we find the component it belongs to using getPointsComponent. If this point belongs to 2 components, we simply return a pair with this element twice. Else we find if we have already encountered this component before and if we have, then add this vertex into the corresponding list. Otherwise we create a new list.
        So, in effect, we have partitioned the vertices into lists such that two points belonging to a list belong to the same component, and two points belonging to different lists belong to different components.
        Then we take a component, and find distance of vertices of this component to the vertices of all other components we haven't already found distances for.

    - static updateCentroids(): O(np + nc * log(nc))
        This updates the centroids of all the components. We iterate through the list of all points, segregate these into their components and for each component of the point, we add it's x, y, z coordinates to the component and also increment the numNodes of that component. This takes O(np) time, np being number of points. Then we find the centroid of each component using the things we just calculated and add to an ArrayList, and sort that. This takes O(nc * log(nc)) time, nc being the number of components.

All these functions mostly do the work required for the queries. But for the sake of completion, here's what is being done for the queries:

1. ADD_TRIANGLE: O(nt + log(np) + log(ne))
    Just calling constructor of Triangle.

2. TYPE_MESH: O(1)
    Type of mesh is stored in Edge class, and is updated on each triangle addition.

3. BOUNDARY_EDGES: O(n log n)
    Taking the boundaryEdges linked list we have maintained in Edge class, copy it to an array and then sort it. This takes O(n log n) time, n being the number of boundary edges.

4. COUNT_CONNECTED_COMPONENTS: O(1)
    We have this count stored in the ConnectedComponent class.

5. NEIGHBORS_OF_TRIANGLE: O(nt)
    We take the ArrayList of neighbors of the triangle (which we find in O(log(nt)) time) and then copy it to an array, which takes O(nt) time, nt being number of triangles.

6. EDGE_NEIGHBOR_TRIANGLE: O(log(nt))
    We find the triangle in O(log(nt)) time and then return it's edge neighbors.

7. VERTEX_NEIGHBOR_TRIANGLE: O(log(nt))
    We find the triangle and then return it's vertex neighbors directly.

8. EXTENDED_NEIGHBOR_TRIANGLE: O(nt)
    We find the triangle in O(log(nt)) time, and the find it's extended neighbor triangles in O(nt) time.

9. INCIDENT_TRIANGLES: O(log(np) + nt)
    We find the point in O(log(np)) time and then copy it's neighbors into an array in O(nt) time. 

10. NEIGHBORS_OF_POINT: O(np)
    We find the point in O(log(np)) time and then copy it's Point neighbors into an array in O(np) time.

11. EDGE_NEIGHBORS_OF_POINT: O(log(np) + ne)
    We find the point in O(log(np)) time and then copy it's Edge neighbors into an array in O(ne) time.

12. FACE_NEIGHBORS_OF_POINT: Same as INCIDENT_TRIANGLES

13. IS_CONNECTED: O(log(nt))
    We find the two triangles in O(log(nt)) time and we just check if they belong to the same component or not.

14. TRIANGLE_NEIGHBOR_OF_EDGE: O(log(ne) + nt)
    We find the edge in O(log(ne)) time and copy it's triangle neighbors to an array in O(nt) time.

15. CENTROID: O(np + nc * log(nc))
    If we already have the centroids precomputed, then we just return those. Else we call the updateCentroids method of ConnectedComponent, which takes O(np + nc * log(nc)) time.

16. CENTROID_OF_COMPONENT: O(np + nc * log(nc))
    We find point and the component to which it belongs in O(log(np)) time, and then do the work mentioned in previous CENTROID query.

17. MAXIMUM_DIAMETER: O(m + n)
    We call the maxDiameter function of Triangle class.

18. CLOSEST_COMPONENTS: O(n ^ 2)
    We call the closestComponents method of ConnectedComponent class. 