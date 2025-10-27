package main.environment.animal;

import main.core.Entity;

public class Animal extends Entity {
    private String status;
    private String type;

    public Animal() {
        super();
        this.status = "Healthy";
        this.type = "Unknown";
    }

    public Animal(String name, double mass, String type){
        super(name, mass);
        this.status = "Healthy";
        this.type = type;
    }
}
