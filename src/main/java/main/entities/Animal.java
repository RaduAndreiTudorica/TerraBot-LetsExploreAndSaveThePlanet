package main.entities;

import main.core.Entity;
import main.core.Section;

public class Animal extends Entity {
    private String status;
    private String type;

    public Animal(String name, double mass, Section section, String status, String type){
        super(name, mass, section);

        this.status = status;
        this.type = type;
    }
}
