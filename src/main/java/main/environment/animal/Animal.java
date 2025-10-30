package main.environment.animal;

import main.core.Entity;
import main.core.Section;
import main.environment.plant.Plant;
import main.environment.soil.Soil;
import main.environment.water.Water;
import java.util.List;
import java.util.ArrayList;

public class Animal extends Entity {
    private String status;
    private String type;
    private double intakeRate = 0.08;
    private double possibilityToAttack;
    private double attackProbability;
    private boolean isScanned = false;


    public Animal() {
        super();
        this.status = "hungry";
        this.type = "Unknown";
    }

    public Animal(String name, double mass, Section section, String type){
        super(name, mass, section);
        this.status = "hungry";
        this.type = type;
        this.possibilityToAttack = getAttackPossibilityByType(type);
        this.attackProbability = calculateAttackProbability();

    }

    private double getAttackPossibilityByType(String type){
        return switch(type.toLowerCase()) {
            case "herbivore" -> 85;
            case "carnivore" -> 30;
            case "omnivore" -> 60;
            case "detritivore" -> 90;
            case "parasite" -> 10;
            default -> 0;
        };
    }

    public double calculateAttackProbability() {
        return (100 - possibilityToAttack) / 10.0;
    }

    public void feed(Section section) {
        if("sick".equalsIgnoreCase(status)) {
            return;
        }

        Soil soil = section.getSoil();
        Plant plant = section.getPlant();
        Water water = section.getWater();
        Animal prey = section.getAnimal() == this ? null : section.getAnimal();

        boolean ate = false;

        if("Carnivore".equalsIgnoreCase(type) || "Parasite".equalsIgnoreCase(type)) {
            if(prey != null) {
                this.mass += prey.getMass();
                soil.setOrganicMatter(soil.getOrganicMatter() + 0.5);
                section.setAnimal(null);
                ate = true;
            }
        }

        if(!ate) {
            if(plant != null && plant.isScanned() && water != null && water.isScanned()) {
                double waterToDrink = Math.min(this.mass * intakeRate, water.getMass());
                water.setMass(water.getMass() -  waterToDrink);
                this.mass += (plant.getMass() + waterToDrink);
                soil.setOrganicMatter(soil.getOrganicMatter() + 0.8);
                section.setPlant(null);
                ate = true;
            } else if(plant != null && plant.isScanned()) {
                this.mass += plant.getMass();
                soil.setOrganicMatter(soil.getOrganicMatter() + 0.5);
                section.setPlant(null);
                ate = true;
            } else if(water != null && water.isScanned()) {
                double waterToDrink = Math.min(this.mass * intakeRate, water.getMass());
                water.setMass(water.getMass() -  waterToDrink);
                this.mass += waterToDrink;
                soil.setOrganicMatter(soil.getOrganicMatter() + 0.5);
                ate = true;
            }
        }
        this.status = ate ? "well-fed" : "hungry";
    }

    public void interactWithEnvironment(Section section) {
        if("well-fed".equalsIgnoreCase(status)) {
            return;
        }

        Water water = section.getWater();
        if (water != null && water.isScanned()) {
            water.setContaminantIndex(water.getContaminantIndex() + 0.3);
        }
    }

    public void markScanned() {
        this.isScanned = true;
    }
    public boolean isScanned() {
        return this.isScanned;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPossibilityToAttack() {
        return this.possibilityToAttack;
    }

    public void setPossibilityToAttack(double possibilityToAttack) {
        this.possibilityToAttack = possibilityToAttack;
    }

}
