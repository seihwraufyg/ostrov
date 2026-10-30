package island.model;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    private final int x, y;
    private final List<Animal> animals = new ArrayList<>();
    private int plants = 0;

    public Cell(int x, int y) { this.x = x; this.y = y; }

    public int getX() { return x; }
    public int getY() { return y; }
    public synchronized List<Animal> getAnimals() { return new ArrayList<>(animals); }

    public synchronized int getPlants() { return plants; }
    public synchronized void addPlants(int n) { plants = Math.min(island.config.SimulationConfig.MAX_PLANTS_PER_CELL, plants + n); }
    public synchronized void removePlants(int n) { plants = Math.max(0, plants - n); }

    public synchronized void addAnimal(Animal a) { animals.add(a); }
    public synchronized boolean removeAnimal(Animal a) { return animals.remove(a); }

    public synchronized long countSpecies(String species) {
        return animals.stream().filter(a -> a.species.equals(species)).count();
    }

    public synchronized boolean canAddSpecies(String species, int max) {
        return countSpecies(species) < max;
    }

    public synchronized List<Animal> snapshot() { return new ArrayList<>(animals); }

    public synchronized void removeAllDead() { animals.removeIf(Animal::isDead); }
}