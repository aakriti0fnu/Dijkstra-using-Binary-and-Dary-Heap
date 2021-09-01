/**
 * Class HeapNode is used to maintain array[HeapNode type objects] in Class Heap.
 */
public class HeapNode {
    public int vertex;
    public int distance;

    public HeapNode(int vertex, int distance){
        this.vertex = vertex;
        this.distance = distance;
    }

    public HeapNode(){}
}