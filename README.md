

This project implements and compares two classical **Minimum Spanning Tree (MST)** algorithms:

**Prim’s Algorithm**

**Kruskal’s Algorithm**

The goal is to determine the most efficient algorithm for constructing an MST within a **city transportation network**, modeled as a weighted undirected graph where:

**Vertices (V)** represent city locations (stations, intersections, etc.)

**Edges (E)** represent possible connections (roads, tracks, etc.) with associated **weights** (cost, distance, or time).

##  Implemented Classes

### `Edge`
Represents a connection between two vertices.

```java
public class Edge implements Comparable<Edge> {
    public int from;
    public int to;
    public double weight;
}
```

### `Graph`
Stores the list of edges and provides methods to add them.

```java
public class Graph {
    public int V;
    public List<Edge> edges;

    public Graph() {
        this.V = 0;
        this.edges = new ArrayList<>();
    }

    public void addEdge(int from, int to, double weight) {
        edges.add(new Edge(from, to, weight));
    }
}
```

### PrimAlgorithm & KruskalAlgorithm
Both algorithms are implemented to calculate:

**Total cost** of the MST  

**Execution time (ms)** for performance comparison

## Results

See results table comparing Prim and Kruskal execution times and costs across 30 graphs.

## Analysis: Which Algorithm Is Faster?

Both algorithms always produce **the same MST cost**, but their **execution time** varies depending on graph characteristics.

### When Prim’s Algorithm is Faster
When the **graph is dense** (many edges relative to vertices)

Because Prim’s algorithm efficiently expands from a single starting vertex using a **priority queue (min-heap)**, minimizing redundant edge checks

###  When Kruskal’s Algorithm is Faster

When the **graph is sparse** (fewer edges)

Kruskal sorts all edges first, so it performs better when the number of edges is relatively small compared to vertices

### Observations

For **small to medium graphs**, both algorithms run almost instantly.

For **large graphs (V > 700)**:

Prim is generally slightly **faster on dense graphs**
  - 
Kruskal can be **faster on sparse graphs**, depending on sorting overhead and union-find efficiency.

## Conclusion

| Algorithm | Best For | Complexity | Implementation Note |
|------------|-----------|-------------|----------------------|
| **Prim’s** | Dense Graphs | O(E log V) | Uses a priority queue |
| **Kruskal’s** | Sparse Graphs | O(E log E) | Uses union-find structure |

**Result:** Both algorithms produce identical MSTs, but **Prim’s algorithm** tends to perform slightly better in dense, connected city networks — making it the preferred choice for **urban transportation modeling**.




