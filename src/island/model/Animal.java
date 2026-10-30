package island.model;

import island.behavior.AnimalRegistry;
import island.behavior.EatTable;
import island.config.SimulationConfig;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal {
    protected String species;
    protected double weight;
    protected int maxPerCell;
    protected int speed;
    protected double foodNeed;
    protected double saturation;
    private boolean dead = false;
    private int starvationTicks = 0;
    private static final int STARVATION_LIMIT = 3;

    protected Animal(String species) {
        this.species = species;
        AnimalRegistry.Spec spec = AnimalRegistry.get(species);
        if (spec == null) {
            throw new IllegalArgumentException("Неизвестный вид животного: " + species);
        }
        this.weight = spec.weight;
        this.maxPerCell = spec.maxPerCell;
        this.speed = spec.speed;
        this.foodNeed = spec.foodNeed;
        this.saturation = foodNeed;
    }

    public String getSpecies() { return species; }
    public double getWeight() { return weight; }
    public int getMaxPerCell() { return maxPerCell; }
    public int getSpeed() { return speed; }
    public double getFoodNeed() { return foodNeed; }
    public boolean isHungry() { return foodNeed > 0 && saturation < foodNeed; }
    public boolean isDead() { return dead; }
    public abstract boolean isPredator();

    public void beginTurn() {
        if (!dead && foodNeed > 0) {
            saturation = Math.max(0, saturation - foodNeed);
        }
    }

    public boolean eat(Cell cell) {
        if (dead || foodNeed <= 0) return false;
        boolean ate = false;

        if (!isPredator() && cell.getPlants() > 0) {
            double need = Math.max(0, foodNeed - saturation);
            double amount = Math.min(need, cell.getPlants() * Plant.WEIGHT);
            if (amount > 0) {
                int plantsToRemove = (int) Math.ceil(amount / Plant.WEIGHT);
                cell.removePlants(plantsToRemove);
                saturation += amount;
                ate = true;
            }
        }

        for (Animal other : cell.snapshot()) {
            if (saturation >= foodNeed || other == this || other.isDead()) break;
            int chance = EatTable.chance(species, other.species);
            if (chance <= 0 || ThreadLocalRandom.current().nextInt(100) >= chance) continue;

            if (cell.removeAnimal(other)) {
                double amount = Math.min(foodNeed - saturation, other.weight);
                saturation += amount;
                other.starve();
                ate = true;
            }
        }
        return ate;
    }

    public void finishTurn() {
        if (dead || foodNeed <= 0) return;
        if (saturation <= 0) {
            starvationTicks++;
            if (starvationTicks >= STARVATION_LIMIT) dead = true;
        } else {
            starvationTicks = 0;
        }
    }

    public void starve() { dead = true; }
    public double getSaturation() { return saturation; }
    public boolean getSaturationFull() { return saturation >= foodNeed; }

    public Animal reproduce(Cell cell) {
        if (dead || foodNeed > 0 && saturation < foodNeed * 0.5) return null;
        long same = cell.snapshot().stream()
                .filter(a -> a != this && !a.isDead() && a.species.equals(species))
                .count();
        if (same == 0 || ThreadLocalRandom.current().nextDouble() >= SimulationConfig.BREED_PROBABILITY) {
            return null;
        }
        if (cell.countSpecies(species) >= maxPerCell) return null;
        return AnimalRegistry.get(species).factory.get();
    }

    public int[] chooseDirection() {
        int dx = ThreadLocalRandom.current().nextInt(-speed, speed + 1);
        int dy = ThreadLocalRandom.current().nextInt(-speed, speed + 1);
        return new int[]{dx, dy};
    }
}
