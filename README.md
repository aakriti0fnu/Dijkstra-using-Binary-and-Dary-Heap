## Dijkstra using Binary Heap and D-ary Heap
### Introduction:
This is an implementation and comparison of the 
 Dijkastra’s single source algorithm using Binary Heap and Min - ary Heap

### Dijkstra’s single source algorithm:
It is a greedy algorithm for finding the shortest
paths between nodes in a graph from a single source. This is asymptotically the
fastest known single-source shortest-path algorithm for arbitrary directed graphs
with unbounded non-negative weights. Our objective is to do experiments for
runtime and number of edge weight comparisons. This algorithm can be run on each
node of the graph and have results combine to produce

### Min Heap Data structure:
A min-heap is a binary tree such that - the data
contained in each node is less than (or equal to) the data in that node’s
children.For edge weight comparisons, we implemented our own heap data
structure which was required to perform decrease key and extract min
operations for Dijkastra’s algorithm. With these operations our Heap can act
as the priority queue needed for the algorithm.

### D-ary Heap Data Structure:

The d-ary heap is a priority queue data structure,
    a generalization of the binary heap in which the nodes have d children
    instead of 2.This data structure allows decrease priority operations to be
    performed more quickly than binary heaps, at the expense of slower delete
    minimum operations. This tradeoff leads to better running times for
    algorithms such as Dijkstra's algorithm in which decreased priority
    operations are more common than delete min operations. This is the second
    data structure we implemented for better comparison of edge weight in
    Dijkstra’s shortest path algorithm


### Implementation Description:
All graphs for this project were represented as an adjacency matrix. This matrix was
implemented as a two dimensional array in java. This array is created by reading by reading
the .gph file supplied to std in and is then passed as an argument to any functions where it
is used.
Dijkstra's algorithm was implemented using the method provided in Introduction to
Algorithms[2]. The priority queue was implemented in one of the two following ways.
- **D-ary heap implementation**
D-ary Heap is implemented in consideration to be used for Dijkstra's as a priority queue. A
heap class and HeapNode class are created for this implementation. Class DHeap
implements a d-ary minimum heap data structure satisfying two properties: It must be a
complete d - tree and the parent key must be minimum among all of its d children. Class
HeapNode represents a data structure whose object when created stores two values vertex
and distance. It is also used to create an array of HeapNode type objects in DHeap class. The
major operation of the Heap class are as follows:

- `percUp()` - percolate the newly added node up to its proper position in the binary minHeap

- `percDown()` - Iterative method to restore the property of binary minHeap

- `decreasekey()` - Decrease distance corresponding to a Vertex to a new value and restore min
heap property by percolating up.

- `extractMin()` - remove the top node( from index 1) from the binary minHeap and percolate
down(to fix the violated minHeap's property)

- `insert()` - insert a new node at the end of minHeap and

 - `percolate up`(if the minHeap's
property is violated)`

As d ary heap is a heap having d children , consequently to calculate the child, it uses:
d*(i-1) + k+1
where d is the no of children, i is the index of a parent for which child is to be calculated,
and k={1,2...d} which child out of all d children to be found
Whenever a new node is inserted in the heap, it takes HeapNode type object and calls
percUp() to maintain the property of the d-ary minheap and whenever the deletion of a
node happens, it calls the percDown() which removes the root node and put the last node
in the root node position and then swaps until node reaches to its right position in heap.
Both percUp() and percDown() operations are implemented as an iterative approach.

DecreaseKey operation is a very common operation in Dijkstra's, it will change the distance
corresponding to a vertex in HeapNode object to a new value and then call percUp() to
restore the heap property.
A part of calculating a total number of comparisons is, to sum up the total number of
Comparisons that happened during extractMin operation and DecreaseKey operation
respectively. With each dijkstra call(with a source), creates a new priorityQueue object,
then calculate total number of Comparisons for
- `DecreaseKey operation`: whenever upon each call to DecreaseKey operation happens in
dijkstra logic, we calculate how many times in percUp operation's swaps have happened
and in end returned a globally maintained variable totalNumHeapPercUp.
- `extractMin operation` : whenever, upon each call to extractMin operation happens in
dijkstra logic, we calculate how many times in percDown operation's swaps have happened
and in end returned a globally maintained variable totalNumHeapPercDown.
This data structure improves the runtime of Dijkstra's as compared to binary heap.
Min Heap Implementation:
Min heap is implemented in consideration to be used for Dijkstra’s as a priority queue. It is
a heap with 2 children and the parent key is minimum among both of its children . The
major classes Dheap and HeapNode are kept the same along with major operations namely
insert, extractmin, DecreaseKey, percUp, percDown.
Both percUp() and percDown() operations are implemented as an iterative approach.
To get the child of binary min heap, the implementation used 2*(i-1) + k+1
where 2 is the no of children, i is the index of a parent for which child is to be calculated,
and k={1,2} which child out of both children to be found.
Min Heap is a primitive data structure to be used with dijkstra’s which is less efficient as
compared to D-ary heap for optimizing the runtime.
The most common DecreaseKey operation is not very fast in binary heap because
performing a DecreaseKey we change the priority of a node in the tree and repeatedly swap
it up with its parent until it either hits the root or its priority ends up becoming smaller. In
worst case the number of swaps are given by the height of the heap which is O(lgn) in
binary heap whereas for d-ary heap, the height of tree is given by O(logd n) which is less for
d > 2 and as a result with increase in number of children, the height of heap decreases and
operations become fast, thus improving the runtime.
