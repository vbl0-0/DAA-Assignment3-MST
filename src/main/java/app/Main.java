package app;

import com.google.gson.*;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject input = gson.fromJson(new FileReader("src/main/resources/input.json"), JsonObject.class);
            JsonArray graphs = input.getAsJsonArray("graphs");

            List<JsonObject> results = new ArrayList<>();

            // CSV header
            StringBuilder csvBuilder = new StringBuilder();
            csvBuilder.append("Graph ID,Vertices,Edges,Prim Cost,Kruskal Cost,Prim Time (ms),Kruskal Time (ms)\n");

            int graphCounter = 0;

            for (JsonElement g : graphs) {
                graphCounter++;
                JsonObject graphObj = g.getAsJsonObject();
                int id = graphObj.get("id").getAsInt();

                List<String> nodes = new ArrayList<>();
                for (JsonElement n : graphObj.getAsJsonArray("nodes"))
                    nodes.add(n.getAsString());

                List<Edge> edges = new ArrayList<>();
                for (JsonElement e : graphObj.getAsJsonArray("edges")) {
                    JsonObject eo = e.getAsJsonObject();
                    edges.add(new Edge(
                            eo.get("from").getAsString(),
                            eo.get("to").getAsString(),
                            eo.get("weight").getAsInt()
                    ));
                }

                Graph graph = new Graph(nodes, edges);

                // Run Prim
                PrimMST.PrimResult primResult = new PrimMST().findMST(graph);

                // Run Kruskal
                KruskalMST.KruskalResult kruskalResult = new KruskalMST().findMST(graph);

                JsonObject resultObj = new JsonObject();
                resultObj.addProperty("graph_id", id);

                JsonObject inputStats = new JsonObject();
                inputStats.addProperty("vertices", nodes.size());
                inputStats.addProperty("edges", edges.size());
                resultObj.add("input_stats", inputStats);

                resultObj.add("prim", buildAlgoObject(primResult, gson));
                resultObj.add("kruskal", buildAlgoObject(kruskalResult, gson));

                results.add(resultObj);

                // Добавляем строку в CSV
                csvBuilder.append(String.format(
                        "%d,%d,%d,%d,%d,%s,%s\n",
                        id,
                        nodes.size(),
                        edges.size(),
                        primResult.totalCost,
                        kruskalResult.totalCost,
                        String.format(Locale.US, "%.3f", primResult.timeMs * 1.0),
                        String.format(Locale.US, "%.3f", kruskalResult.timeMs * 1.0)
                ));
            }

            JsonObject output = new JsonObject();
            output.add("results", gson.toJsonTree(results));

            // Сохраняем JSON
            File jsonFile = new File("output.json");
            try (FileWriter writer = new FileWriter(jsonFile)) {
                gson.toJson(output, writer);
            }

            // Сохраняем CSV
            // --- Save CSV nicely formatted ---
            File csvFile = new File("results.csv");
            try (FileWriter csvWriter = new FileWriter(csvFile)) {

                csvWriter.write("=============================================================\n");
                csvWriter.write(" City Transportation Network - MST Results Comparison\n");
                csvWriter.write("=============================================================\n");
                csvWriter.write(String.format("%-10s | %-8s | %-8s | %-10s | %-12s | %-12s | %-12s\n",
                        "Graph ID", "Vertices", "Edges", "Prim Cost", "Kruskal Cost", "Prim Time (ms)", "Kruskal Time (ms)"));
                csvWriter.write("-------------------------------------------------------------\n");

                for (JsonObject r : results) {
                    JsonObject inputStats = r.getAsJsonObject("input_stats");
                    JsonObject prim = r.getAsJsonObject("prim");
                    JsonObject kruskal = r.getAsJsonObject("kruskal");

                    csvWriter.write(String.format(Locale.US,
                            "%-10d | %-8d | %-8d | %-10d | %-12d | %-12.3f | %-12.3f\n",
                            r.get("graph_id").getAsInt(),
                            inputStats.get("vertices").getAsInt(),
                            inputStats.get("edges").getAsInt(),
                            prim.get("total_cost").getAsInt(),
                            kruskal.get("total_cost").getAsInt(),
                            prim.get("execution_time_ms").getAsDouble(),
                            kruskal.get("execution_time_ms").getAsDouble()
                    ));
                }

                csvWriter.write("=============================================================\n");
                System.out.println("✅ Results saved to output.json and results.csv");

            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println("✅ Results saved successfully!");
            System.out.println("📁 JSON: " + jsonFile.getAbsolutePath());
            System.out.println("📄 CSV: " + csvFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JsonObject buildAlgoObject(Object algoResult, Gson gson) {
        JsonObject obj = new JsonObject();
        if (algoResult instanceof PrimMST.PrimResult) {
            PrimMST.PrimResult res = (PrimMST.PrimResult) algoResult;
            obj.add("mst_edges", gson.toJsonTree(res.mstEdges));
            obj.addProperty("total_cost", res.totalCost);
            obj.addProperty("operations_count", res.operations);
            obj.addProperty("execution_time_ms", res.timeMs);
        } else if (algoResult instanceof KruskalMST.KruskalResult) {
            KruskalMST.KruskalResult res = (KruskalMST.KruskalResult) algoResult;
            obj.add("mst_edges", gson.toJsonTree(res.mstEdges));
            obj.addProperty("total_cost", res.totalCost);
            obj.addProperty("operations_count", res.operations);
            obj.addProperty("execution_time_ms", res.timeMs);
        }
        return obj;
    }
}
