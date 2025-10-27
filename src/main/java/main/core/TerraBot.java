package main.core;

import java.util.*;

public class TerraBot {
    private int x = 0;
    private int y = 0;

    private int energy;
    private boolean isCharging = false;
    private int chargeEndTimestamp = 0;

    private Map<String, List<String>> knowledgeBase = new HashMap<>();
    private Map<String, Object> inventory = new HashMap<>();

    public TerraBot(int initialEnergy) {
        this.energy = initialEnergy;
    }

    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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

    public boolean isCharging() {
        return isCharging;
    }

    public int getChargeEndTimestamp() {
        return chargeEndTimestamp;
    }

    public void startCharging() {
        this.isCharging = true;
        this.chargeEndTimestamp = 0;
    }
    public void stopCharging() {
        this.isCharging = false;
        this.chargeEndTimestamp = 0;
    }

    public void learnFact(String topic, String fact) {
        knowledgeBase.putIfAbsent(topic, new ArrayList<>());
        knowledgeBase.get(topic).add(fact);
    }

    public Map<String, List<String>> getKnowledgeBase() {
        return knowledgeBase;
    }

    public void addToInventory(String name, Object item) {
        inventory.put(name, item);
    }
    public Map<String, Object> getInventory() {
        return inventory;
    }
}
