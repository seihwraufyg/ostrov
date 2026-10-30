package island.animals;

import island.model.Herbivore;

public class Caterpillar extends Herbivore {
    public Caterpillar() {
        super("Caterpillar");
    }

    @Override
    public int[] chooseDirection() {
        return new int[]{0, 0}; // Гусеница не двигается
    }
}