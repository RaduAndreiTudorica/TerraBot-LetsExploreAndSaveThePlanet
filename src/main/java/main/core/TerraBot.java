package main.core;

import main.environment.air.Air;
import main.environment.animal.Animal;
import main.environment.plant.Plant;
import main.environment.soil.Soil;

import java.util.*;

public class TerraBot {
    private Section section;

    private int energy;
    private boolean isCharging = false;
    private int chargeEndTimestamp = 0;

    private Map<String, List<String>> knowledgeBase = new LinkedHashMap<>();
    //private Map<String, Object> inventory = new HashMap<>();
    private Set<String> scannedObjectNames = new HashSet<>();

    public TerraBot(int initialEnergy) {
        this.energy = initialEnergy;
    }

    public Section findBestSectionToMove(List<Section> neighbors) {
        Section bestSection = null;
        int minScore = Integer.MAX_VALUE;

        for (Section neighbor : neighbors) {
            if(neighbor == null) {
                continue;
            }

            int score = calculateMoveScore(neighbor);
            if(score < minScore) {
                minScore = score;
                bestSection = neighbor;
            }
        }
        return bestSection;
    }

    public int calculateMoveScore(Section section) {
        if(section == null) {
            return Integer.MAX_VALUE;
        }

        double sum = 0;
        int count = 0;

        Soil soil = section.getSoil();
        Air air = section.getAir();
        Animal animal = section.getAnimal();
        Plant plant = section.getPlant();

        if (soil != null) {
            sum += soil.getBlockingProbability();
            count++;
        }
        if (air != null) {
            sum += air.getToxicityAQ();
            count++;
        }
        if (animal != null) {
            sum += animal.getAttackProbability();
            count++;
        }
        if (plant != null) {
            sum += plant.getBLockingProbability();
            count++;
        }

        if(count == 0) {
            return 0;
        }

        double mean = Math.abs(sum / count);
        int score = (int) Math.round(mean);

        return score;
    }

    public int getEnergy() {
        return energy;
    }

    public void decreaseEnergy(int amount) {
        this.energy = Math.max(0, this.energy - amount);
    }

    public void increaseEnergy(int amount) {
        this.energy += amount;
    }

    public boolean isCharging(int currentTimestamp) {
        if(isCharging && currentTimestamp >= this.chargeEndTimestamp) {
            isCharging = false;
            chargeEndTimestamp = 0;
        }
        return isCharging;
    }

    public int getChargeEndTimestamp() {
        return chargeEndTimestamp;
    }

    public void startCharging(int currentTimestamp, int timeToCharge) {
        this.isCharging = true;
        this.chargeEndTimestamp = currentTimestamp + timeToCharge;
        increaseEnergy(timeToCharge);
    }
    public void stopCharging() {
        this.isCharging = false;
        this.chargeEndTimestamp = 0;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Section getSection() {
        return section;
    }

    public void learnFact(String topic, String fact) {
        knowledgeBase.putIfAbsent(topic, new ArrayList<>());
        knowledgeBase.get(topic).add(fact);
    }
    public void removeFromInventory(String name) {
        scannedObjectNames.remove(name);
    }

    public Map<String, List<String>> getKnowledgeBase() {
        return knowledgeBase;
    }

    public void addToInventory(String name) {
        scannedObjectNames.add(name);
    }
    public boolean hasScanned(String name) {
        return scannedObjectNames.contains(name);
    }
}