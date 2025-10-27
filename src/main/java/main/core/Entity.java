package main.core;

import java.util.*;

public abstract class Entity {
    protected String name;
    protected double mass;
    protected List<Section> sections = new ArrayList<>();

    public Entity() {
        this.name = "Unknown";
        this.mass = 0.0;

    }

    public Entity(String name, double mass) {
        this.name = name;
        this.mass = mass;
    }

    public void addSection(Section section) {
        sections.add(section);
    }

    public List<Section> getSections() {
        return sections;
    }

}
