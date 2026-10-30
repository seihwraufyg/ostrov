package island.behavior;

import java.util.HashMap;
import java.util.Map;

public final class EatTable {
    private static final Map<String, Integer> TABLE = new HashMap<>();

    static {
        // Волк
        put("Wolf", "Horse", 10); put("Wolf", "Deer", 15); put("Wolf", "Rabbit", 60);
        put("Wolf", "Mouse", 80); put("Wolf", "Goat", 60); put("Wolf", "Sheep", 70);
        put("Wolf", "Boar", 15); put("Wolf", "Buffalo", 10); put("Wolf", "Duck", 40);
        // Удав
        put("Boa", "Fox", 15); put("Boa", "Rabbit", 20); put("Boa", "Mouse", 40);
        put("Boa", "Duck", 10);
        // Лиса
        put("Fox", "Rabbit", 70); put("Fox", "Mouse", 90); put("Fox", "Duck", 60);
        put("Fox", "Caterpillar", 40);
        // Медведь
        put("Bear", "Boa", 80); put("Bear", "Horse", 40); put("Bear", "Deer", 80);
        put("Bear", "Rabbit", 80); put("Bear", "Mouse", 90); put("Bear", "Goat", 70);
        put("Bear", "Sheep", 70); put("Bear", "Boar", 50); put("Bear", "Buffalo", 20);
        put("Bear", "Duck", 10);
        // Орел
        put("Eagle", "Fox", 10); put("Eagle", "Rabbit", 90); put("Eagle", "Mouse", 90);
        put("Eagle", "Duck", 80);
        // Травоядные-хищники
        put("Duck", "Caterpillar", 90);
        put("Boar", "Mouse", 50); put("Boar", "Caterpillar", 90);
        put("Mouse", "Caterpillar", 90);

        String[] herbivores = {"Horse","Deer","Rabbit","Mouse","Goat","Sheep","Boar","Buffalo","Duck"};
        for (String h : herbivores) put(h, "Plant", 100);
        put("Caterpillar", "Plant", 100);
    }

    private static void put(String predator, String prey, int pct) {
        TABLE.put(predator + "->" + prey, pct);
    }

    public static int chance(String predator, String prey) {
        return TABLE.getOrDefault(predator + "->" + prey, 0);
    }

    private EatTable() {}
}