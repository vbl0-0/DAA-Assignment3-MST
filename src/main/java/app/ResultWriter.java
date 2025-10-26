package app;

import java.io.*;
import java.util.*;
import com.google.gson.*;

public class ResultWriter {

    public static void writeDetailedJson(List<JsonObject> results, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject output = new JsonObject();
        output.add("results", gson.toJsonTree(results));

        try (FileWriter writer = new FileWriter(fileName)) {
            gson.toJson(output, writer);
        }
    }

    public static void writeDetailedCsv(List<JsonObject> results, String fileName) throws IOException {
        try (PrintWriter writer = new PrintWriter(fileName)) {

            writer.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════════");
            writer.println("GraphID | Vertices | Edges | Prim Cost | Kruskal Cost | Prim Ops | Kruskal Ops | Prim Time (ms) | Kruskal Time (ms)");
            writer.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════════");

            for (JsonObject res : results) {
                int id = res.get("graph_id").getAsInt();
                JsonObject stats = res.getAsJsonObject("input_stats");

                JsonObject prim = res.getAsJsonObject("prim");
                JsonObject kruskal = res.getAsJsonObject("kruskal");

                writer.printf(Locale.US,
                        "%-8d | %-9d | %-5d | %-10d | %-13d | %-9d | %-12d | %-14.2f | %-17.2f%n",
                        id,
                        stats.get("vertices").getAsInt(),
                        stats.get("edges").getAsInt(),
                        prim.get("total_cost").getAsInt(),
                        kruskal.get("total_cost").getAsInt(),
                        prim.get("operations_count").getAsInt(),
                        kruskal.get("operations_count").getAsInt(),
                        prim.get("execution_time_ms").getAsDouble(),
                        kruskal.get("execution_time_ms").getAsDouble()
                );
            }

            writer.println("═══════════════════════════════════════════════════════════════════════════════════════════════════════════════");
        }
    }
}
