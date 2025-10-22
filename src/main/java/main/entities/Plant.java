package main.entities;

import main.Section;

import java.util.*;

public class Plant extends Entity {

    private static final Map<String, Float> BASE_OXYGEN_MAP;
    private static final Map<String, Float> MATURITY_BONUS_MAP;
    private static final Map<String, Integer> BLOCKING_PROBABILITY_MAP;

    static {
        BASE_OXYGEN_MAP = new HashMap<>();
        BASE_OXYGEN_MAP.put("FloweringPlants", 6.0f);
        BASE_OXYGEN_MAP.put("GymnospermsPlants", 0.0f);
        BASE_OXYGEN_MAP.put("Ferns", 0.0f);
        BASE_OXYGEN_MAP.put("Mosses", 0.8f);
        BASE_OXYGEN_MAP.put("Algae", 0.5f);

        MATURITY_BONUS_MAP = new HashMap<>();
        MATURITY_BONUS_MAP.put("young", 0.2f);
        MATURITY_BONUS_MAP.put("mature", 0.7f);
        MATURITY_BONUS_MAP.put("old", 0.4f);

        BLOCKING_PROBABILITY_MAP = new HashMap<>();
        BLOCKING_PROBABILITY_MAP.put("FloweringPlants", 90);
        BLOCKING_PROBABILITY_MAP.put("GymnospermsPlants", 60);
        BLOCKING_PROBABILITY_MAP.put("Ferns", 30);
        BLOCKING_PROBABILITY_MAP.put("Mosses", 40);
        BLOCKING_PROBABILITY_MAP.put("Algae", 20);
    }

    private String type;
    private String status;
    private int plant_possibility;
    private double growthLevel;

    public Plant(String name, double mass, List<Section> sections, String type) {
        super(name, mass,  sections);

        this.type = type;
        this.status = "young";
        this.plant_possibility = BLOCKING_PROBABILITY_MAP.getOrDefault(type, 0);
    }

    public float getOxygenProduction() {
        float baseOxygen =  BASE_OXYGEN_MAP.getOrDefault(this.type, 0.0f);
        float maturityBonus = MATURITY_BONUS_MAP.getOrDefault(this.status, 0.0f);

        return baseOxygen + maturityBonus;
    }

    public double getBLockingProbability() {
        return this.plant_possibility / 100.0;
    }
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String get_Status() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPlant_possibility() {
        return this.plant_possibility;
    }
}

