package main.environment.air;

import main.core.Entity;

import java.util.NavigableMap;
import java.util.TreeMap;

public abstract class Air extends Entity {
    public static final double TOXICITY_MAX_SCORE = 100.0;

    private static final NavigableMap<Double, String> QUALITY_MAP = new TreeMap<>();

    static {
        QUALITY_MAP.put(0.0, "Poor");
        QUALITY_MAP.put(40.0, "Moderate");
        QUALITY_MAP.put(70.0, "Good");
    }

    private String type;
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double toxicityAQ;
    private double airQuality;
    private String qualityStatus;

    public Air() {
        super();
        this.type = "Unknown";
        this.humidity = 0.0;
        this.temperature = 0.0;
        this.oxygenLevel = 0.0;
        this.toxicityAQ = 0.0;
        this.airQuality = 0.0;
    }

    public Air(String name, double mass,
               String type, double humidity, double temperature,
               double oxygenLevel) {

        super(name, mass);
        this.type = type;
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }

    abstract public double calculateQuality();
    abstract public double updateQuality();

    public void interpretQuality() {
        this.qualityStatus = QUALITY_MAP.floorEntry(this.airQuality).getValue();
    }

    public double calculateToxicityAQ() {
        double toxicityAQ = 100 * (1 - getAirQuality() / TOXICITY_MAX_SCORE);

        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0;

        this.toxicityAQ = toxicityAQ;
        return toxicityAQ;
    }

    public double getHumidity() {
        return this.humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
        calculateQuality();
        calculateToxicityAQ();
    }

    public double getTemperature() {
        return this.temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
        calculateQuality();
        calculateToxicityAQ();
    }

    public double getOxygenLevel() {
        return this.oxygenLevel;
    }
    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
        calculateQuality();
        calculateToxicityAQ();
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
