## Dijkstra using Binary Heap and D-ary Heap

#### How to run the project?

- Navigate to the `src/` directory 

- Compile all files

    `javac *.java`

- Run experiments on `All Pairs Shortest Path Algorithm`

    Note: 
    - Command syntax:

        ```
        java AllPairShortestPath \
                [alg type] \
                [number of childeren in d heap]  \
                < [input file] \
                > [optional: output file] \
                2> [optional: Statistics File]
        ```
        - It outputs to stdout separated by lines with -1 
        - Statistics on the run are sent to stderr
        - both files can be optionally redirected
        - input must be fed into stdin
    - use `dd` for dijkstra with D array heaps

      use `db` for dijkstra with binary heap

    ex:
    ```
    java AllPairShortestPath db < test.gph 
    java AllPairShortestPath dd 3 <test.gph
    ```
----

Credits:

Matthew Pudlo \
Christian Morris \
Nagashree Mulukunte Nagaraju
