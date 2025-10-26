package app;

import java.util.*;

public class KruskalMST {
    public static class KruskalResult {
        List<Edge> mstEdges = new ArrayList<>();
        int totalCost = 0;
        int operations = 0;
        long timeMs;
    }

    private Map<String, String> parent = new HashMap<>();

    private String find(String node) {
        if (!parent.get(node).equals(node))
            parent.put(node, find(parent.get(node)));
        return parent.get(node);
    }

    private void union(String a, String b) {
        parent.put(find(a), find(b));
    }

    public KruskalResult findMST(Graph graph) {
        long start = System.nanoTime();
        KruskalResult result = new KruskalResult();

        for (String node : graph.nodes) parent.put(node, node);

        List<Edge> edges = new ArrayList<>(graph.edges);
        Collections.sort(edges);

        for (Edge e : edges) {
            result.operations++;
            String rootA = find(e.from);
            String rootB = find(e.to);
            if (!rootA.equals(rootB)) {
                result.totalCost += e.weight;
                result.mstEdges.add(e);
                union(rootA, rootB);
            }
        }

        result.timeMs = (System.nanoTime() - start) / 1_000_000;
        return result;
    }
}
