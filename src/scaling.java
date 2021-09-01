import java.io.FileWriter;
import java.util.Scanner;
import java.util.ArrayList;
class Scaling {
    final static int   INF                   = 9999;
    static double      numComparisons        = 0;

    public static long totalComparisons      = 0;
    public static long extractMinComparison  = 0;
    public static long decreaseKeyComparison = 0;

    /**
     * Preforms a single itteration of dijkstra's algorithum finding the single
     * source shortest path. This follows the method used in the textboox.
     * this is used as a control against the scaling algorithum.
     * Arguments: 
     * int start: the index of the node to start on int
     * graph[][]:graph to preform on represented as an ajacency matrix int size:
     * the number of nodes in a graph Returns: int[][]: return the shortest
     * distance array for the graph with that start node
     */

    static int[] dijkstra ( int start, int graph[][], int size ) {
        // System.out.println("start node: " + (start + 1));
        int[] dist = new int[size]; // distance array is the solution

        // Step 1 :Initlize the single source
        for ( int i = 0; i < size; i++ ) {
            dist[i] = INF;
        }
        dist[start] = 0;

        // Step 2: Intilize the selected set to have none selected
        // Use a boolean array to keep track of what elements are the selected
        // set
        // Default value is false
        Boolean[] selected = new Boolean[size];

        // step 3: Initilize the Priority queue Q =G.V
        MinHeap priorityQueue = new MinHeap( size );

        // Make the distace to every other node INF since no nodes are selected
        for ( int i = 0; i < size; i++ ) {
            priorityQueue.insert( new HeapNode( i, INF ) );
        }
        priorityQueue.decreaseKey( 0, start );// start value should be zero
                                              // distance to itself

        // step 4: Loop while the queue is not empty
        HeapNode u;
        while ( priorityQueue.heapSize() != 0 ) {
            // step 5: Extract the lowest edge that adds a new node
            u = priorityQueue.extractMin();
            // System.out.println("selected node:" + (u.vertex+1));
            // step 6: Remove u from the queue and add it to the selected set
            selected[u.vertex] = true;
            // step 7: Loop over all ajacent nodes to the one added and update
            // the queue weights
            for ( int i = 0; i < size; i++ ) {
                // select if a node is ajacent from the graph
                if ( graph[u.vertex][i] != INF ) {
                    // Step 8: relax each edge
                    totalComparisons++;
                    if ( dist[i] > dist[u.vertex] + graph[u.vertex][i] ) {
                        dist[i] = dist[u.vertex] + graph[u.vertex][i];
                        try {
                            priorityQueue.decreaseKey( dist[i], i );
                        }
                        catch ( Exception e ) {
                        }
                    }
                }
            }
        }
        // For each dijkstra call, that create a new priorityQueue object(means
        // totalNumHeapPercUp=0)
        // whenever, upon each call to decreaseKey ops, calculates how many
        // times in percUp ops, swaps have happened?
        decreaseKeyComparison += priorityQueue.totalNumHeapPercUp;
        extractMinComparison += priorityQueue.totalNumHeapPercDown;
        return dist;
    }
    /**
     * prints the solution matrix that is returned from the desired algoritum
     *
     * Arguments: int[][] dist: the solution matrix to print
     */
    static void printSolution ( int dist[][], int nodeCount ) {
        int size = dist.length;

        System.out.println( nodeCount );
        for ( int i = 0; i < size; ++i ) {
            for ( int j = 0; j < size; ++j ) {
                if ( dist[i][j] == INF ) {
                    System.out.println( dist[j][i] );
                }
                else {
                    System.out.println( dist[i][j] );
                }
            }
            if ( i < ( size - 1 ) ) {
                System.out.println( "-1" );
            }
        }
    }

    // prints out adjacency matrix created from input
    static void printAdjacencyMatrix ( int[][] graph, int nodes ) {
        try {
            FileWriter output = new FileWriter( "adjMatrix.txt" );
            for ( int i = 0; i < nodes; i++ ) {
                for ( int j = 0; j < nodes; j++ ) {
                    output.write( graph[i][j] + "\t" );
                }
                output.write( "\n" );
            }
            output.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    //the start node is alwayse the first node
    static int[] scalingAlgorithm ( int[][] graph, int heaviestEdge, int numNodes, int numEdges ) {
        //calculates how many itterations are needed using change of base to get log base 2
        int i = (int)( Math.log( heaviestEdge )/ Math.log(2)); 

        //Initalize the innital distance array to be 0, Lines 7 - 12 of the MIT paper
        //are not needed as we are running this algorithum on connected graphs
        int[] distance = new int[numNodes];
        int[] extraDistance = new int[numNodes];
        for ( int j = 0; j < numNodes; j++ ) {
                distance[j] = 0;
        }
        distance[0] = 0;

        //create the priority queue and the node to be extracted
        MinHeap Q = new MinHeap( numNodes );
        HeapNode u;
        i++;
        while (i > 0){
            i--;//decrment the counter
            //intitalize all distances to Infinity
            for ( int j = 0; j < numNodes; j++ ) {
                extraDistance[j] = INF;
            }
            //set the distance to the source to be zero
            extraDistance[0] = 0;
            
            //initalize the queue
            for ( int j = 0; j < numNodes; j++ ) {
                Q.insert( new HeapNode( j, INF ) );
            }
            Q.decreaseKey( 0, 0 );
            //prefomre dijaksras on the sub optimal solution.
            while(Q.heapSize() != 0){
                u = Q.extractMin(); 
                for ( int v = 0; v < numNodes; v++ ) {
                    if ( graph[u.vertex][v] != INF ) {
                        //use right shift to strip off most significant bit at a time
                        int l = (graph[u.vertex][v] >> i) + 2 * (distance[u.vertex]-distance[v]); //u is a heap node so u.vertex is needed to access the array
                        //update distances
                        totalComparisons++;
                        if(extraDistance[v] > extraDistance[u.vertex] + l){
                            extraDistance[v] = extraDistance[u.vertex] + l;
                            try{
                                Q.decreaseKey(extraDistance[v], v);
                                }
                            catch ( Exception e ) {
                            }
                        }
                    }
                }

            }
            //scale up the sub optimal solution to optimal
            for (int j = 0; j < numNodes; j++) {
                if (distance[j] < INF) {
                    distance[j] = 2 * distance[j] + extraDistance[j];
                }
            }
        }
        return distance;
    }

    static int[] scalingAlgorithm_optimized ( int[][] graph, int heaviestEdge, int numNodes, int numEdges ) {
        int i = (int)( Math.log( heaviestEdge )/ Math.log(2)); //need to use change of base to get 
        //lines 1 and 2 of MIT Paper
        int[] distance = new int[numNodes];
        int[] extraDistance = new int[numNodes];
        for ( int j = 0; j < numNodes; j++ ) {
                distance[j] = 0;
        }
        //line 4 of Mit Paper
        distance[0] = 0;

        //line 5 of mit paper
        PriorityQueue Q = new PriorityQueue( numNodes , numEdges, heaviestEdge);
        int u;

        //line 13 of mit paper
        //System.out.println("The first i: " + i);
        i++;
        while (i > 0){
            i--; //line 14 of mit paper
            System.out.println("i: " + i);
            //line 15-16 of mit paper
            for ( int j = 0; j < numNodes; j++ ) {
                extraDistance[j] = INF;
            }
            //line 17 of Mit Paper
            extraDistance[0] = 0;
            //line 18 of mit paper
            for ( int j = 0; j < numNodes; j++ ) {
                Q.insert( j, Q.max/2);
            }
            Q.decreaseKey( 0, 0 , Q.max/2);//line 19 of MIT paper
            while(Q.getSize() != 0){
                u = Q.extractMin(); //line 21
                //System.out.println("Run Number: "+ i + " U.vertex: " + (u.vertex + 1));
                for ( int v = 0; v < numNodes; v++ ) {
                    if ( graph[u][v] != INF ) {
                        int l = (graph[u][v] >> i) + 2 * (distance[u]-distance[v]); //line 23 Change U.vertex to a temp variable
                        //System.out.println("w_i : " + (graph[u.vertex][v] >> i));
                        //System.out.println("l: " + l);
                        if(extraDistance[v] > extraDistance[u] + l){ //24
                            int oldDistance = extraDistance[v];
                            extraDistance[v] = extraDistance[u] + l;//25
                            try{
                                Q.decreaseKey(extraDistance[v], v, oldDistance);
                                }
                            catch ( Exception e ) {
                            }
                        }
                    }
                }

            }
            for (int j = 0; j < numNodes; j++) {
                System.out.print(" before Distance " + (j + 1 ) + " : " + distance[j]);
                System.out.println(" extraDistance: " + extraDistance[j]);
                //System.out.println(" before extraDistance " + (j + 1) + " : " + extraDistance[j]);

                if (distance[j] < INF) {
                    distance[j] = 2 * distance[j] + extraDistance[j];
                }
                //System.out.println(" After Distance " + (j + 1) + " : " + distance[j]);
                //System.out.println(" After extraDistance " + (j + 1) + " : " + extraDistance[j]);
            }
            

        }
        return distance;
    }

    // Driver program to test above function
    public static void main ( String[] args ) {

        // create variables used for algo execution
        long startTime, endTime;
        int graph[][] = null;
        int numNodes = 0;
        int numEdges = 0;
        int heaviestEdge = 0;

        // covert input file to ajacency matrix
        /**
         * CODE TO COVERT GRAPH FILE TO AJACENCY MATRIX GOES HERE, Should be
         * stored in the graph variable
         */
        // File input = new File( args[1] );
        Scanner graphRead;
        try {
            graphRead = new Scanner( System.in );
            while ( graphRead.hasNextLine() ) {
                String line = graphRead.nextLine();
                if ( line.length() > 0 ) {
                    String current[] = line.split( " " );
                    String type = current[0];
                    if ( type.equalsIgnoreCase( "g" ) ) {
                        numNodes = Integer.parseInt( current[1] );
                        numEdges = Integer.parseInt( current[2] );
                        graph = new int[numNodes][numNodes];
                    }
                    else if ( type.equalsIgnoreCase( "e" ) ) {
                        int weight = Integer.parseInt( current[3] );
                        graph[Integer.parseInt( current[1] ) - 1][Integer.parseInt( current[2] ) - 1] = weight;
                        graph[Integer.parseInt( current[2] ) - 1][Integer.parseInt( current[1] ) - 1] = weight;
                        if ( weight > heaviestEdge ) {
                            heaviestEdge = weight;
                        }
                    }
                }
            }
            for ( int i = 0; i < numNodes; i++ ) {
                for ( int j = 0; j < numNodes; j++ ) {
                    if ( graph[i][j] == 0 && i != j ) {
                        graph[i][j] = INF;
                    }
                }
            }
            graphRead.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

        System.out.println("numNodes" + numNodes);
        System.out.println("graph.length" + graph.length);
        printAdjacencyMatrix(graph,numNodes);


        // run the algo
        int[] s;
        if ( args[0].equalsIgnoreCase( "d" ) ) {
            // start the clock
            startTime = System.nanoTime();
            s = dijkstra( 0, graph, graph.length );

            // stop the clock
            endTime = System.nanoTime();
            double totalTime = (double) ( endTime - startTime ) / 1_000_000_000;
            double total = Math.round( totalTime * 10 ) / 10.0;
            // print the output
            for(int j =0 ; j < graph.length; j++){
                System.out.println(s[j]);
            }
            System.err.println( "runtime " + total + " seconds" );
        }
        else if ( args[0].equalsIgnoreCase( "s" ) ) {
            //start the clock
            startTime = System.nanoTime();
            int[] scaleGraph = scalingAlgorithm( graph, heaviestEdge, numNodes, numEdges );
            // stop the clock
            endTime = System.nanoTime();

            double totalTime = (double) ( endTime - startTime ) / 1_000_000_000;
            double total = Math.round( totalTime * 10 ) / 10.0;
            for(int j = 0; j < scaleGraph.length; j++){
                System.out.println(scaleGraph[j]);
            }
            System.err.println( "runtime " + total + " seconds" );
        }
        else {
            System.out.println("Please supply a valid algorithm to run. d for dijkstra's or s for scaling algorithm" );
            return;
        }
        /**
        System.out.println("dijkstra solution:");

        int[] s = dijkstra( 0, graph, graph.length );
        for(int j =0 ; j < graph.length; j++){
            System.out.println(s[j]);
        }

            System.out.println("Scaling Solution:");
            int[] scaleGraph = scalingAlgorithm( graph, heaviestEdge, numNodes, numEdges );
            for(int j = 0; j < scaleGraph.length; j++){
                System.out.println(scaleGraph[j]);
            }
            */
    }
}
