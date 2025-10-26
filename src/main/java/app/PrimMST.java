package app;

import java.util.*;

public class PrimMST {
    public static class PrimResult {
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;
        long timeMs;
    }

    public PrimResult findMST(Graph graph) {
        long start = System.nanoTime();
        PrimResult result = new PrimResult();

        Set<String> visited = new HashSet<>();
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        String startNode = graph.nodes.get(0);
        visited.add(startNode);
        pq.addAll(graph.adjList.get(startNode));

        while (!pq.isEmpty() && visited.size() < graph.nodes.size()) {
            Edge e = pq.poll();
            result.operations++;
            if (visited.contains(e.to)) continue;
            visited.add(e.to);
            result.totalCost += e.weight;
            result.mstEdges.add(e);
            for (Edge next : graph.adjList.get(e.to)) {
                if (!visited.contains(next.to)) pq.add(next);
            }
        }

        result.timeMs = (System.nanoTime() - start) / 1_000_000;
        return result;
    }
}
