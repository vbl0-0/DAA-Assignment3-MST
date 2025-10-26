package mst;

import static org.junit.jupiter.api.Assertions.*;
import graph.*;
import org.junit.jupiter.api.Test;

public class MSTTest {
    @Test
    void testSmallGraph() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(1, 2, 2);
        g.addEdge(2, 3, 3);
        g.addEdge(3, 0, 4);

        Result prim = PrimMST.compute(g);
        Result kruskal = KruskalMST.compute(g);

        assertEquals(prim.totalCost, kruskal.totalCost);
        assertEquals(3, prim.mstEdges.size());
        assertEquals(3, kruskal.mstEdges.size());
        assertTrue(prim.totalCost > 0);
    }
}
