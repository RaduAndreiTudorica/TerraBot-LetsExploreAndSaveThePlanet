package main.environment.plant;

import main.core.Entity;
import main.core.Section;
import main.environment.air.Air;

import java.util.*;

public class Plant extends Entity {

    private static final Map<String, Float> BASE_OXYGEN_MAP;
    private static final Map<String, Float> MATURITY_BONUS_MAP;
    private static final Map<String, Integer> BLOCKING_PROBABILITY_MAP;
    private static final String[] MATURITY_STAGES = {"young", "mature", "old", "dead"};

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
    private int plantPossibility;
    private double growthLevel;
    private double oxygenProduction;
    private boolean isScanned = false;

    public Plant() {
        super();
        this.type = "Unknown";
        this.status = "young";
        this.plantPossibility = 0;
        this.growthLevel = 0.0;
    }

    public Plant(String name, double mass, Section section, String type) {
        super(name, mass, section);

        this.type = type;
        this.status = "young";
        this.plantPossibility = BLOCKING_PROBABILITY_MAP.getOrDefault(type, 0);
        this.growthLevel = 0.0;
        calculateOxygenProduction();
    }

    public float calculateOxygenProduction() {
        float baseOxygen =  BASE_OXYGEN_MAP.getOrDefault(this.type, 0.0f);
        float maturityBonus = MATURITY_BONUS_MAP.getOrDefault(this.status, 0.0f);

        setOxygenProduction(baseOxygen + maturityBonus);
        return baseOxygen + maturityBonus;
    }

    public void interactWithEnvironment(Section section, int iteration) {
        Air air = section.getAir();
        if(air == null || isDead()) {
            return;
        }

        double produced = this.oxygenProduction;
        air.setOxygenLevel(air.getOxygenLevel() + produced);

        this.growthLevel += produced;

        if(this.growthLevel > 1.0) {
            this.growthLevel = 0.0;
            advancematurity();
            if(!isDead()) {
                this.oxygenProduction = calculateOxygenProduction();
            }
        }
    }

    private void advancematurity() {
        int index = Arrays.asList(MATURITY_STAGES).indexOf(this.status);
        if(index < MATURITY_STAGES.length - 1) {
            this.status = MATURITY_STAGES[index + 1];
        }
    }

    public void addGrowthLevel(double growthLevel) {
        if(isDead()) {
            return;
        }
        this.growthLevel += growthLevel;
    }

    public double getBLockingProbability() {
        return this.plantPossibility / 100.0;
    }
    public String getType() {
        return this.type;
    }

    public void markScanned() {
        this.isScanned = true;
    }
    public boolean isScanned() {
        return this.isScanned;
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

    public int getPlantPossibility() {
        return this.plantPossibility;
    }

    public double getOxygenProduction() {
        return this.oxygenProduction;
    }

    public void setOxygenProduction(double oxygenProduction) {
        this.oxygenProduction = oxygenProduction;
    }

    public void beEaten() {
        this.status = "dead";
    }

    public boolean isDead() {
        return this.status.equals("dead");
    }

}

