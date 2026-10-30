package island.stats;

import island.model.Animal;
import island.model.Cell;
import island.model.Island;

import java.util.*;
import java.util.stream.Collectors;

public final class Statistics {
    public static String report(Island island, long tick) {
        Map<String, Long> counts = new TreeMap<>();
        long totalPlants = 0;
        for (Cell c : island.allCells()) {
            totalPlants += c.getPlants();
            for (Animal a : c.snapshot()) {
                if (!a.isDead()) counts.merge(a.getSpecies(), 1L, Long::sum);
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== Тик ").append(tick).append(" ===\n");
        sb.append("Растений: ").append(totalPlants).append("\n");
        sb.append(counts.entrySet().stream()
                .map(e -> e.getKey() + "=" + e.getValue())
                .collect(Collectors.joining(", ")));
        sb.append("\nВсего животных: ").append(island.totalAnimals());
        return sb.toString();
    }

    private Statistics() {}
}