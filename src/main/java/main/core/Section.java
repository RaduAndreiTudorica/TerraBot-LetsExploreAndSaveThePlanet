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
    private List<Plant> plants = new ArrayList<>();
    private List<Animal> animals = new ArrayList<>();
    private List<Water> waters = new ArrayList<>();

    public Section(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void setSoil(Soil soil) { this.soil = soil; }
    public Soil getSoil() { return soil; }
    public void setAir(Air air) { this.air = air; }
    public Air getAir() { return air; }
    public List<Plant> getPlants() { return plants; }
    public void addPlant(Plant plant) { plants.add(plant); }
    public void addAnimal(Animal animal) { animals.add(animal); }
    public void addWater(Water water) { waters.add(water); }
}
