package main.environment.air;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CommandInput;
import main.core.Section;

public class TemperateAir extends Air {
    private double pollenLevel;
    private static final String[] SEASONS = {"Spring", "Summer", "Autumn", "Winter"};
    @JsonIgnore
    private String currentSeason = SEASONS[0];

    public TemperateAir() {
        super();
        this.pollenLevel = 0.0;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public TemperateAir(String name, double mass, Section section, String type, double humidity,
                        double temperature, double oxygenLevel, double pollenLevel) {
        super(name, mass, section, type, humidity, temperature, oxygenLevel);

        this.pollenLevel = pollenLevel;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    @Override
    public double calculateQuality() {
        double airQuality = (this.oxygenLevel * 2) + (this.humidity * 0.7) - (this.pollenLevel * 0.1);

        airQuality = Math.max(0, Math.min(100, airQuality));
        airQuality = Math.round(airQuality * 100.0) / 100.0;

        return airQuality;
    }

    @Override
    public double updateQuality() {
        double seasonPenalty = currentSeason.equalsIgnoreCase(SEASONS[0]) ? 15 : 0;

        this.toxicityAQ = calculateToxicityAQ();
        return seasonPenalty;
    }

    @Override
    public double getMaxScore() {
        return 84.0;
    }

    @Override
    public boolean applyWeatherEvent(CommandInput command) {
        if ("newSeason".equals(command.getType())) {
            this.currentSeason = command.getSeason();
            this.airQuality = updateQuality();
            interpretQuality();
            return true;
        }
        return false;
    }

    public double getPollenLevel() {
        return this.pollenLevel;
    }

    public void setPollenLevel(double pollenLevel) {
        this.pollenLevel = pollenLevel;
        recalc();
    }

    public String getCurrentSeason() {
        return this.currentSeason;
    }

    public void setCurrentSeason(String currentSeason) {
        this.currentSeason = currentSeason;
        this.airQuality = updateQuality();
        interpretQuality();
    }
}
