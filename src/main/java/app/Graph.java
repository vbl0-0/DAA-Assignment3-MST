package app;

import java.util.*;

public class Graph {
    List<String> nodes;
    List<Edge> edges;
    Map<String, List<Edge>> adjList = new HashMap<>();

    public Graph(List<String> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
        for (String node : nodes) adjList.put(node, new ArrayList<>());
        for (Edge e : edges) {
            adjList.get(e.from).add(e);
            adjList.get(e.to).add(new Edge(e.to, e.from, e.weight)); // undirected
        }
    }
}
