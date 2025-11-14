package main.environment.air;

import fileio.CommandInput;
import main.core.Entity;
import main.core.Section;
import main.environment.animal.Animal;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.*;

public abstract class Air extends Entity {
    private static final NavigableMap<Double, String> QUALITY_MAP = new TreeMap<>();

    static {
        QUALITY_MAP.put(0.0, "poor");
        QUALITY_MAP.put(40.0, "moderate");
        QUALITY_MAP.put(70.0, "good");
    }

    protected String type;
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    @JsonIgnore
    protected double toxicityAQ;
    protected double airQuality;
    @JsonIgnore
    protected String qualityStatus;

    public Air() {
        super();
        this.type = "Unknown";
        this.humidity = 0.0;
        this.temperature = 0.0;
        this.oxygenLevel = 0.0;
        this.toxicityAQ = 0.0;
        this.airQuality = 0.0;
    }

    public Air(String name, double mass, Section section,
                String type, double humidity, double temperature,
                double oxygenLevel) {

        super(name, mass, section);
        this.type = type;
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
        recalc();
    }

    abstract public double calculateQuality();
    abstract public double updateQuality();
    @JsonIgnore
    abstract double getMaxScore();
    public abstract boolean applyWeatherEvent(CommandInput command);

    public void interpretQuality() {
        this.qualityStatus = QUALITY_MAP.floorEntry(this.airQuality).getValue();
    }

    public void interactWithEnvironment(Section section, int iteration) {
        Animal animal = section.getAnimal();
            if (animal != null && animal.isScanned()) {
                if(this.toxicityAQ > 60.0) {
                    animal.setStatus("Sick");
                }
            }
    }

    public double calculateToxicityAQ() {
        double toxicityAQ = 100 * (1 - getAirQuality() / getMaxScore());

        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0;

        return toxicityAQ;
    }

    public double getHumidity() {
        return this.humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
        recalc();
    }

    public double getTemperature() {
        return this.temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
        recalc();
    }

    public double getOxygenLevel() {
        this.oxygenLevel = Math.max(0, Math.min(100, this.oxygenLevel));
        this.oxygenLevel = Math.round(this.oxygenLevel * 100.0) / 100.0;
        return this.oxygenLevel;
    }
    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
        recalc();
    }

    public double getToxicityAQ() {
        return this.toxicityAQ;
    }

    void setToxicityAQ(double toxicityAQ) {
        this.toxicityAQ = toxicityAQ;
    }

    public double getAirQuality() {
        return this.airQuality;
    }

    void setAirQuality(double airQuality) {
        this.airQuality = airQuality;

    }

    protected void recalc() {
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public  String getQualityStatus() {
        return this.qualityStatus;
    }

    public String getType() {
        return this.type;
    }
}
