package main.core;

public abstract class Entity {
    private String name = null;
    private double mass = 0;
    private Section sections;

    public Entity(String name, double mass,  Section sections) {
        this.name = name;
        this.mass = mass;
        this.sections = sections;

    }
}
