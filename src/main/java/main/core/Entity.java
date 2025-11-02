package main.core;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Entity {
    protected String name;
    protected double mass;
    @JsonIgnore
    protected Section section;

    public Entity() {
        this.name = "Unknown";
        this.mass = 0.0;

    }

    public Entity(String name, double mass, Section section) {
        this.name = name;
        this.mass = mass;
        this.section = section;
    }

    public String getName() {
        return name;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

}
