    import java.util.*;

    public class PriorityQueue {


        ArrayList<ArrayList<Integer>> Q;
        int                   size;
        int                   m;
        int max;

        @SuppressWarnings ( "unchecked" )
        public PriorityQueue ( int n, int m, int N) {
            size = 0;
            this.m = m;
            this.max = ((n - 1) * N) + 1;
            Q = new ArrayList<ArrayList<Integer>>(this.max);
            //for ( int i = 0; i < Q.size(); i++ ) {
                //Q.get(i).add(new ArrayList<HeapNode>());
            //}
        }

        public void insert ( int vertex, int distance ) {
            Q.get(distance).add(vertex);
            size++;
        }

        public int getSize () {
            return size;
        }

        public int extractMin () {
            if ( size == 0 ) {
                return 9999;
            }
            else {
                for ( int i = 0; i < Q.size(); i++ ) {
                    if ( Q.get(i).size() > 0 ) {
                        int h = Q.get(i).get( 0 );
                        Q.get(i).remove( 0 );
                        return h;
                    }
                }
            }
            return 9999;
        }

        public void decreaseKey ( int newDistance, int vertex, int oldDistance ) {
            Q.get(oldDistance).remove( vertex );
            Q.get(newDistance).add( vertex );
        }
    }