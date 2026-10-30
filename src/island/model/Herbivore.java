package island.model;

public abstract class Herbivore extends Animal {
    protected Herbivore(String species) { super(species); }
    @Override public boolean isPredator() { return false; }
}