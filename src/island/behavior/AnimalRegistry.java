package island.behavior;

import island.model.Animal;
import island.animals.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class AnimalRegistry {
    public static class Spec {
        public final double weight;
        public final int maxPerCell;
        public final int speed;
        public final double foodNeed;
        public final Supplier<Animal> factory;

        public Spec(double weight, int maxPerCell, int speed, double foodNeed, Supplier<Animal> factory) {
            this.weight = weight; this.maxPerCell = maxPerCell;
            this.speed = speed; this.foodNeed = foodNeed; this.factory = factory;
        }
    }

    private static final Map<String, Spec> SPECS = new HashMap<>();

    static {
        SPECS.put("Wolf",   new Spec(50, 30, 3, 8, Wolf::new));
        SPECS.put("Boa",    new Spec(15, 30, 1, 3, Boa::new));
        SPECS.put("Fox",    new Spec(8, 30, 2, 2, Fox::new));
        SPECS.put("Bear",   new Spec(500, 5, 2, 80, Bear::new));
        SPECS.put("Eagle",  new Spec(6, 20, 3, 1, Eagle::new));
        SPECS.put("Horse",  new Spec(400, 20, 4, 60, Horse::new));
        SPECS.put("Deer",   new Spec(300, 20, 4, 50, Deer::new));
        SPECS.put("Rabbit", new Spec(2, 150, 2, 0.45, Rabbit::new));
        SPECS.put("Mouse",  new Spec(0.05, 500, 1, 0.01, Mouse::new));
        SPECS.put("Goat",   new Spec(60, 140, 3, 10, Goat::new));
        SPECS.put("Sheep",  new Spec(70, 140, 3, 15, Sheep::new));
        SPECS.put("Boar",   new Spec(400, 50, 2, 50, Boar::new));
        SPECS.put("Buffalo",new Spec(700, 10, 3, 100, Buffalo::new));
        SPECS.put("Duck",   new Spec(1, 200, 4, 0.15, Duck::new));
        SPECS.put("Caterpillar", new Spec(0.01, 1000, 0, 0, Caterpillar::new));
    }

    public static Spec get(String name) { return SPECS.get(name); }
    public static Map<String, Spec> all() { return SPECS; }

    private AnimalRegistry() {}
}