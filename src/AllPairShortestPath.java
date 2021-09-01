import java.io.FileWriter;
import java.util.Scanner;

class AllPairShortestPath {
    final static int   INF                   = 99999;
    static double      numComparisons        = 0;

    public static long totalComparisons      = 0;
    public static long extractMinComparison  = 0;
    public static long decreaseKeyComparison = 0;

    static int[][] dijkstraAllPairs ( int graph[][] ) {
        int size = graph.length;
        int sol[][] = new int[size][size];
        // prefomr dijkstra's alg size times, starting once on each node
        for ( int i = 0; i < size; i++ ) {
            sol[i] = dijkstra( i, graph, size );
        }
        totalComparisons = totalComparisons + extractMinComparison + decreaseKeyComparison;
        return sol;
    }

    /**
     * Preforms a single itteration of dijkstra's algorithum finding the single
     * source shortest path. This follows the method used in the textboox.
     * Arguments: int start: the index of the node to start on int
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
     * Preforms an itteration of dijkstra's algorithum for every node being the
     * start node to find the all pairs shortest path.
     *
     * Arguments: int graph[][]:graph to preform on represented as an ajacency
     * matrix
     *
     * Returns: int[][]: return the shortest distance matrix for the graph
     */
    static int[][] dijkstraAllPairs_dheap ( int graph[][], int d ) {
        int size = graph.length;
        int sol[][] = new int[size][size];
        // prefomr dijkstra's alg size times, starting once on each node
        for ( int i = 0; i < size; i++ ) {
            sol[i] = dijkstra_dheap( i, graph, size, d );
        }
        totalComparisons = totalComparisons + extractMinComparison + decreaseKeyComparison;
        return sol;

    }

    /**
     * Preforms a single itteration of dijkstra's algorithum finding the single
     * source shortest path. This follows the method used in the textboox.
     *
     * Arguments: int start: the index of the node to start on int
     * graph[][]:graph to preform on represented as an ajacency matrix int size:
     * the number of nodes in a graph ind d: the number to use in the d heap
     *
     * Returns: int[][]: return the shortest distance array for the graph with
     * that start node
     */
    static int[] dijkstra_dheap ( int start, int graph[][], int size, int d ) {
        // System.out.println("start node: " + (start + 1));
        int[] dist = new int[size]; // distance array is the solution

        // Step 1 :Initialize the single source
        for ( int i = 0; i < size; i++ ) {
            dist[i] = INF;
        }
        dist[start] = 0;

        // Step 2: Initialize the selected set to have none selected
        // Use a boolean array to keep track of what elements are the selected
        // set
        // Default value is false
        Boolean[] selected = new Boolean[size];
        // step 3: Initialize the Priority queue Q =G.V

        DHeap priorityQueue = new DHeap( size, d );
        // Heap priorityQueue = new Heap(size );

        // Make the distance to every other node INF since no nodes are selected
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
            // step 7: Loop over all adjacent nodes to the one added and update
            // the queue weights
            for ( int i = 0; i < size; i++ ) {
                // select if a node is adjacent from the graph
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
        // times in percUp ops's swaps have happened!
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
                    output.write( graph[i][j] + " " );
                }
                output.write( "\n" );
            }
            output.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }

    }

    // Driver program to test above function
    public static void main ( String[] args ) {
        // check for correct number of command line args
        System.out.println( args.length );
        if ( args.length < 1 ) {
            System.out.println( "Please supply the correct number of command line arguments" );
            return;
        }

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

        int s[][];

        // run the algo
        if ( args[0].equalsIgnoreCase( "db" ) ) {
            // start the clock
            startTime = System.nanoTime();
            s = AllPairShortestPath.dijkstraAllPairs( graph );
            // stop the clock
            endTime = System.nanoTime();
            double totalTime = (double) ( endTime - startTime ) / 1_000_000_000;
            double total = Math.round( totalTime * 10 ) / 10.0;
            // print the output
            printSolution( s, numNodes );
            System.err.println( "runtime " + total + " seconds" );
            System.err.println( "comparisons " + totalComparisons );
            System.err.println( "extractMinComparison " + extractMinComparison );
            System.err.println( "decreaseKeyComparison " + decreaseKeyComparison );
        }
        else if ( args[0].equalsIgnoreCase( "dd" ) ) {
            // start the clock
            startTime = System.nanoTime();
            if ( args.length != 2 ) {
                System.out.println( "Please specify what size D array the heap should use" );
            }
            s = dijkstraAllPairs_dheap( graph, Integer.parseInt( args[1] ) );
            // stop the clock
            endTime = System.nanoTime();
            double totalTime = (double) ( endTime - startTime ) / 1_000_000_000;
            double total = Math.round( totalTime * 10 ) / 10.0;

            // print the output
            printSolution( s, numNodes );
            System.err.println( "runtime " + total + " seconds" );
            System.err.println( "comparisons " + totalComparisons );
            System.err.println( "extractMinComparison " + extractMinComparison );
            System.err.println( "decreaseKeyComparison " + decreaseKeyComparison );
        }
        else {
            System.out.println(
                    "Please supply a valid algorithm to run. d for dijkstra's, fw for floyd warshall, or scaling for scaling algorithm" );
            return;
        }
    }
}
