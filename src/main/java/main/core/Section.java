package main.core;

import main.environment.animal.Animal;
import main.environment.plant.Plant;
import main.environment.air.Air;
import main.environment.soil.Soil;
import main.environment.water.Water;
import java.util.*;


public class Section {
    private int x;
    private int y;

    private Soil soil;
    private Air air;
    private Water water;

    private Plant plant;
    private Animal animal;

    public Section(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Section getSection() {
        return this;
    }

    public void setSoil(Soil soil) { this.soil = soil; }
    public Soil getSoil() { return soil; }
    public void setAir(Air air) { this.air = air; }
    public Air getAir() { return air; }
    public void setWater(Water water) { this.water = water; }
    public Water getWater() { return water; }
    public void setPlant(Plant plant) { this.plant = plant; }
    public Plant getPlant() { return plant; }
    public void setAnimal(Animal animal) { this.animal = animal; }
    public Animal getAnimal() { return animal; }
}
