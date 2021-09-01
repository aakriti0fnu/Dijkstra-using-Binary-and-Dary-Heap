## Dijkstra using Binary Heap and D-ary Heap

#### How to run the project?

- Compile all files

    `javac -d bin src/*.java `

- Run experiments on `All Pairs Shortest Path Algorithm`
    
    Command syntax:
    ```
    java AllPairShortestPath \
            [alg type] \
            [number of childeren in d heap]  \
            < [input file] \
            > [optional: output file] \
            2> [optional: Statistics File]
    ```
    where:
        - outputs to stdout separated by lines with -1 
        - statistics on the run are sent to stderr
        - both files can be optionally redirected
        - input must be fed into stdin

    Example:
    
    - use `db` for dijkstra with binary heap
     ```
     # to get output on console
     java -cp bin AllPairShortestPath db < data/input/test.gph
     
     # to capture output to files.
     java -cp bin AllPairShortestPath \
                  db \
                  < data/input/test.gph \
                  > data/output/test.out \
                  2> data/stats/test.txt

     ```
    - use `dd` for dijkstra with D-array heaps      
    ```
    # to get output on console
    java -cp bin AllPairShortestPath dd 3 < data/input/test.gph

    # to capture output to files.
    java -cp bin AllPairShortestPath \
            dd \
            3 \
            < data/input/test.gph \
            > data/output/test.out \
            2> data/stats/test.txt
    ```
----

Credits:

Matthew Pudlo \
Christian Morris \
Nagashree Mulukunte Nagaraju
