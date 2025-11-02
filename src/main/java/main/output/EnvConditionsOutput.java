package main.output;

import com.fasterxml.jackson.annotation.JsonInclude;
import main.environment.air.Air;
import main.environment.animal.Animal;
import main.environment.plant.Plant;
import main.environment.soil.Soil;
import main.environment.water.Water;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnvConditionsOutput {
    private Soil soil;
    private Plant plants;
    private Animal animals;
    private Water water;
    private Air air;

    public Soil getSoil() { return soil; }
    public void setSoil(Soil soil) { this.soil = soil; }

    public Plant getPlants() { return plants; }
    public void setPlants(Plant plants) { this.plants = plants; }

    public Animal getAnimals() { return animals; }
    public void setAnimals(Animal animals) { this.animals = animals; }

    public Water getWater() { return water; }
    public void setWater(Water water) { this.water = water; }

    public Air getAir() { return air; }
    public void setAir(Air air) { this.air = air; }
}
