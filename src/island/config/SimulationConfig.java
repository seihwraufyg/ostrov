package island.config;

import java.util.Map;

public class SimulationConfig {
    public static int ISLAND_WIDTH = 100;
    public static int ISLAND_HEIGHT = 20;
    public static long TICK_MILLIS = 1000;

    public static int MAX_PLANTS_PER_CELL = 200;
    public static double PLANT_GROWTH_PROBABILITY = 0.3;

    public static Map<String, Integer> START_ANIMALS = Map.ofEntries(
            Map.entry("Wolf", 30), Map.entry("Boa", 30), Map.entry("Fox", 30),
            Map.entry("Bear", 5), Map.entry("Eagle", 20),
            Map.entry("Horse", 20), Map.entry("Deer", 20), Map.entry("Rabbit", 150),
            Map.entry("Mouse", 500), Map.entry("Goat", 140), Map.entry("Sheep", 140),
            Map.entry("Boar", 50), Map.entry("Buffalo", 10), Map.entry("Duck", 200),
            Map.entry("Caterpillar", 1000)
    );

    public static double MOVE_PROBABILITY = 0.5;
    public static double BREED_PROBABILITY = 0.1;

    public static boolean STOP_WHEN_NO_ANIMALS = true;
    public static int MAX_TICKS = 1000;
}