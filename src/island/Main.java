package island;

import island.config.SimulationConfig;
import island.model.Animal;
import island.model.Cell;
import island.model.Island;
import island.stats.Statistics;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final Island island =
            new Island(SimulationConfig.ISLAND_WIDTH, SimulationConfig.ISLAND_HEIGHT);

    public static void main(String[] args) throws InterruptedException {
        validateConfig();
        island.populateStart();

        long tick = 0;
        while (tick < SimulationConfig.MAX_TICKS) {
            tick++;

            island.growPlants();
            animalLifecycle();

            System.out.println(Statistics.report(island, tick));

            if (SimulationConfig.STOP_WHEN_NO_ANIMALS && island.totalAnimals() == 0) {
                System.out.println("Все животные вымерли. Остановка.");
                break;
            }

            Thread.sleep(SimulationConfig.TICK_MILLIS);
        }
        System.out.println("Симуляция завершена на тике " + tick);
    }

    private static void animalLifecycle() {
        List<Cell> cells = island.allCells();
        List<List<Animal>> snapshots = new java.util.ArrayList<>(cells.size());
        for (Cell cell : cells) snapshots.add(cell.snapshot());

        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            List<Animal> snapshot = snapshots.get(i);
            for (Animal animal : snapshot) {
                if (animal.isDead()) continue;

                animal.beginTurn();
                animal.eat(cell);

                Animal child = animal.reproduce(cell);
                if (child != null) cell.addAnimal(child);

                if (ThreadLocalRandom.current().nextDouble() < SimulationConfig.MOVE_PROBABILITY
                        && animal.getSpeed() > 0) {
                    int[] direction = animal.chooseDirection();
                    if (direction[0] != 0 || direction[1] != 0) {
                        island.move(animal, direction[0], direction[1]);
                    }
                }

                animal.finishTurn();
            }
            cell.removeAllDead();
        }
    }

    private static void validateConfig() {
        if (SimulationConfig.ISLAND_WIDTH <= 0 || SimulationConfig.ISLAND_HEIGHT <= 0) {
            throw new IllegalArgumentException("Размеры острова должны быть положительными.");
        }
        if (SimulationConfig.TICK_MILLIS < 0 || SimulationConfig.MAX_TICKS < 0) {
            throw new IllegalArgumentException("Интервал и число тиков не могут быть отрицательными.");
        }
        if (SimulationConfig.MOVE_PROBABILITY < 0 || SimulationConfig.MOVE_PROBABILITY > 1
                || SimulationConfig.BREED_PROBABILITY < 0 || SimulationConfig.BREED_PROBABILITY > 1
                || SimulationConfig.PLANT_GROWTH_PROBABILITY < 0
                || SimulationConfig.PLANT_GROWTH_PROBABILITY > 1) {
            throw new IllegalArgumentException("Вероятности должны находиться в диапазоне от 0 до 1.");
        }
    }
}
