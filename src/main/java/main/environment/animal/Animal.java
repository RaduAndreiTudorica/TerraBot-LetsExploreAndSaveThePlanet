package main.environment.animal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.core.Entity;
import main.core.Section;
import main.environment.plant.Plant;
import main.environment.soil.Soil;
import main.environment.water.Water;
import java.util.List;
import java.util.ArrayList;

public class Animal extends Entity {
    @JsonIgnore
    private String status;
    private String type;
    private double intakeRate = 0.08;
    @JsonIgnore
    private double possibilityToAttack;
    @JsonIgnore
    private double attackProbability;
    @JsonIgnore
    private boolean isScanned = false;
    @JsonIgnore
    private int activationTimestamp = -1;


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
            case "herbivores" -> 85;
            case "carnivores" -> 30;
            case "omnivores" -> 60;
            case "detritivores" -> 90;
            case "parasites" -> 10;
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
            if(prey != null && !prey.isScanned()) {
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
        if(!"well-fed".equalsIgnoreCase(status)) {
            return;
        }

        Water water = section.getWater();
        if (water != null && water.isScanned()) {
            water.setContaminantIndex(water.getContaminantIndex() + 0.3);
        }
    }

    public Section findBestSectionToMove(List<Section> neighbors) {
        Section Priority1 = null;
        Section Priority2_Plant = null;
        Section Priority2_Water = null;
        Section Priority3_Fallback = null;

        for(Section neighbor : neighbors) {
            if (neighbor == null) {
                continue;
            }

            if(Priority3_Fallback == null) {
                Priority3_Fallback = neighbor;
            }

            Plant plant = neighbor.getPlant();
            Water water = neighbor.getWater();

            boolean hasScannedPlant = (plant != null && plant.isScanned());
            boolean hasScannedWater = (water != null && water.isScanned());

            if(hasScannedPlant && hasScannedWater) {
                if(Priority1 == null || water.getWaterQuality() > Priority1.getWater().getWaterQuality()) {
                    Priority1 = neighbor;
                }
            } else if(hasScannedPlant) {
                Priority2_Plant = neighbor;
            } else if(hasScannedWater) {
                if(Priority2_Water == null || water.getWaterQuality() > Priority2_Water.getWater().getWaterQuality()) {
                    Priority2_Water = neighbor;
                }
            }
        }

        if(Priority1 != null) {
            return Priority1;
        }

        if(Priority2_Plant != null) {
            return  Priority2_Plant;
        }

        if(Priority2_Water != null) {
            return Priority2_Water;
        }

        return Priority3_Fallback;
    }

    public void markScanned() {
        this.isScanned = true;
    }
    @JsonIgnore
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

    public double getAttackProbability() {
        return this.attackProbability;
    }

    public int getActivationTimestamp() {
        return activationTimestamp;
    }

    public void setActivationTimestamp(int activationTimestamp) {
        this.activationTimestamp = activationTimestamp;
    }

    public boolean isActive(int currentTimestamp) {
        return isScanned && activationTimestamp != -1 &&
                currentTimestamp >= activationTimestamp + 2;
    }

}
