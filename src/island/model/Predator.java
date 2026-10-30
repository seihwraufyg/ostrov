package island.model;

public abstract class Predator extends Animal {
    protected Predator(String species) { super(species); }
    @Override public boolean isPredator() { return true; }
}