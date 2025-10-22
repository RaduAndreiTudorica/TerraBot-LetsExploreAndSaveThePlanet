package main.entities;

import main.Section;

import java.util.*;

public class Entity {
    private String name = null;
    private double mass = 0;
    private List<Section> sections;

    public Entity(String name, double mass,  List<Section> sections) {
        this.name = name;
        this.mass = mass;
        this.sections = sections;

    }
}
