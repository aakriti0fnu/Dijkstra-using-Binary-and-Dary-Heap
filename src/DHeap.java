
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Class datastructures.daryHeap.Heap implementation is of darrMinHeap satisfying two properties:
 * 1. must be complete binary tree
 * 2. parent key must be minimum among all it's child.
 * Start index=1
 * parent of node i = floor( (i-2)/d+1)
 * child k of node i = d(i-1)+k+1
 * where 1<=k<=d
 */
public class DHeap {

    public int vertices;
    public int currentSize;
    public  HeapNode[] minHeap;
    public int [] pos;
    public int d;
    public  static int  totalNumHeapPercUp = 0;
    public static int  totalNumHeapPercDown = 0;


    /**
     * Constructor
     * empty binary heap has a single 0 first Node (will not be used) and
     * is there so that simple integer division can be used in later methods.
     * @param vertices
     */
    public DHeap(int vertices, int numChild) {
        this.vertices = vertices;
        minHeap = new HeapNode[vertices + 1];
        pos = new int[vertices];
        minHeap[0] = new HeapNode();
        minHeap[0].distance = Integer.MIN_VALUE;
        minHeap[0].vertex=0;
        currentSize = 0;
        this.d= numChild;
    }
    private int getParent(int i) {
         // parent of ith element
         return (int)Math.floor( (i-2)/ d+1 );
    }
    public int getChild(int i, int k ){
        // kth child of ith ele
        return d*(i-1) + k+1;
    }
    /** Function to check if heap is empty **/
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /** Check if heap is full **/
    public boolean isFull( )
    {
        return currentSize == minHeap.length;
    }

    /**
     * Helper function to print pos array.
     */
    public void printPos() {
        System.out.println("Pos Array"+ Arrays.toString(pos));
    }
    /**
     * Helper function to print minHeap.
     */
    public void print() {
        for (int i = 1; i <=currentSize; i++) {
            System.out.println("V: " + minHeap[i].vertex + " D: " + minHeap[i].distance);
        }
    }

    /**
     * Helper function to get currentSize of minHeap
     * @return currentSize
     */
    public int heapSize(){
        return currentSize;
    }

    /**
     * Helper function to swap nodes.
     * @param Idx1
     * @param Idx2
     */
    public void swapMinHeapNodes(int Idx1, int Idx2) {
        HeapNode temp = minHeap[Idx1];
        minHeap[Idx1] = minHeap[Idx2];
        minHeap[Idx2] = temp;
    }
    /**
     * percolate the newly added node up to its proper position in the minHeap.
     * @param newNodeIdx
     */
    public int percUp(int newNodeIdx) {
        int localComp=0;
        int parentIdx = getParent(newNodeIdx);
        while (newNodeIdx > 0 && minHeap[parentIdx].distance > minHeap[newNodeIdx].distance) {
            //swap the nodes.
            HeapNode newNode = minHeap[newNodeIdx];
            HeapNode parentNode = minHeap[parentIdx];
            pos[newNode.vertex] = parentIdx;
            pos[parentNode.vertex] = newNodeIdx;
            swapMinHeapNodes(newNodeIdx,parentIdx);
            localComp++;
            newNodeIdx = parentIdx;
            parentIdx = getParent(parentIdx);
        }
        return localComp;
    }

    /**
     * insert a new node at the end of minHeap and
     * percolate up(if the minHeap's property is violated)
     * Runtime:  O( log n / log d))
     * @param newNode
     */
    public void insert(HeapNode newNode) {
        if (isFull( ) )
            throw new NoSuchElementException("heap Overflow Exception");
        currentSize++;
        int idx = currentSize;
        minHeap[idx] = newNode;
        pos[newNode.vertex] = idx;
        percUp(idx);
    }

    /**
     * Decrease distance of Vertex to a new value.
     * and restore minheap property by percolating up.
     * Runtime:  O(log n / log d))
     * @param newDist
     * @param vertex
     */
    public void decreaseKey(int newDist, int vertex) {
        int decreaseKeyComp=0;
        int index = pos[vertex];
        HeapNode node = minHeap[index];
        node.distance = newDist;
        decreaseKeyComp = percUp(index);
        totalNumHeapPercUp = totalNumHeapPercUp + decreaseKeyComp;
    }

    private int minChild(int ind)
    {   // 1<=k<=d
        int bestChild = getChild(ind, 1);
        int k = 2;
        int nxt = getChild(ind, k);
        while ((k <= d) && (nxt < currentSize))
        {
            if (minHeap[nxt].distance < minHeap[bestChild].distance )
                bestChild = nxt;
            nxt = getChild(ind, k++);
        }
        return bestChild;
    }

    /**
     * Recursive method to restore the property of minHeap.
     * @param NodeIdx
     */
    public int percDown(int NodeIdx) {
        int child;
        HeapNode tmp = minHeap[NodeIdx];
        int localComp=0;
        while (getChild(NodeIdx, 1) < currentSize)
        {
            child = minChild(NodeIdx);
            HeapNode childNode= minHeap[child];
            if (childNode.distance < tmp.distance){
                minHeap[NodeIdx] = minHeap[child];
            localComp++;}
            else
                break;
            NodeIdx = child;

        }
        minHeap[NodeIdx] = tmp;
        return localComp;
    }
    /**
     * remove the top node( from index 1) from the minHeap.
     * percolate down(to fix the violated minHeap's property)
     * Runtime:  O(d log n / log d))
     * @return rootNode
     */
    public HeapNode extractMin() {
        int extractMinComp=0;
        HeapNode rootNode = minHeap[1];
        HeapNode lastNode = minHeap[currentSize];
        // update position of lastNode
        pos[lastNode.vertex] = 1;
        minHeap[1] = lastNode;
        // remove lastNode from minHeap
        minHeap[currentSize] = null;
        // restore the minHeap's property.
        extractMinComp= percDown(1);
        totalNumHeapPercDown+=extractMinComp;
        currentSize--;
        return rootNode;
    }

}



