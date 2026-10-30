package island.model;

import island.behavior.AnimalRegistry;
import island.config.SimulationConfig;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Island {
    private final Cell[][] grid;
    private final int width, height;

    public Island(int width, int height) {
        this.width = width; this.height = height;
        grid = new Cell[height][width];
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                grid[y][x] = new Cell(x, y);
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Cell getCell(int x, int y) { return grid[y][x]; }

    public void populateStart() {
        Random r = ThreadLocalRandom.current();
        for (var e : SimulationConfig.START_ANIMALS.entrySet()) {
            String species = e.getKey();
            int count = e.getValue();
            for (int i = 0; i < count; i++) {
                int x = r.nextInt(width), y = r.nextInt(height);
                AnimalRegistry.Spec spec = AnimalRegistry.get(species);
                if (spec == null) {
                    throw new IllegalArgumentException("Неизвестный вид в START_ANIMALS: " + species);
                }
                Cell cell = grid[y][x];
                int attempts = 0;
                while (!cell.canAddSpecies(species, spec.maxPerCell) && attempts++ < width * height) {
                    x = r.nextInt(width);
                    y = r.nextInt(height);
                    cell = grid[y][x];
                }
                if (cell.canAddSpecies(species, spec.maxPerCell)) {
                    cell.addAnimal(spec.factory.get());
                }
            }
        }
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                grid[y][x].addPlants(r.nextInt(50));
    }

    public void growPlants() {
        Random r = ThreadLocalRandom.current();
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                if (r.nextDouble() < SimulationConfig.PLANT_GROWTH_PROBABILITY)
                    grid[y][x].addPlants(1 + r.nextInt(3));
    }

    public synchronized void move(Animal a, int dx, int dy) {
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) {
                Cell c = grid[y][x];
                if (c.getAnimals().contains(a)) {
                    int nx = Math.floorMod(x + dx, width);
                    int ny = Math.floorMod(y + dy, height);
                    c.removeAnimal(a);
                    grid[ny][nx].addAnimal(a);
                    return;
                }
            }
    }

    public List<Cell> allCells() {
        List<Cell> list = new ArrayList<>(width * height);
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++) list.add(grid[y][x]);
        return list;
    }

    public long totalAnimals() {
        return allCells().stream().mapToLong(c -> c.snapshot().stream()
                .filter(a -> !a.isDead()).count()).sum();
    }
}