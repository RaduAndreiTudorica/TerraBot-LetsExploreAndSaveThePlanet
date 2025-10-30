package main.environment.air;

import main.core.Entity;
import main.core.Section;
import main.environment.animal.Animal;

import java.util.NavigableMap;
import java.util.TreeMap;

public abstract class Air extends Entity {
    private static final NavigableMap<Double, String> QUALITY_MAP = new TreeMap<>();

    static {
        QUALITY_MAP.put(0.0, "Poor");
        QUALITY_MAP.put(40.0, "Moderate");
        QUALITY_MAP.put(70.0, "Good");
    }

    protected String type;
    protected double humidity;
    protected double temperature;
    protected double oxygenLevel;
    protected double toxicityAQ;
    protected double airQuality;
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
    }

    abstract public double calculateQuality();
    abstract public double updateQuality();
    abstract double getMaxScore();

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
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public double getTemperature() {
        return this.temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();;
        interpretQuality();
    }

    public double getOxygenLevel() {
        return this.oxygenLevel;
    }
    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
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
}
