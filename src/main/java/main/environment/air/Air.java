package main.environment.air;

import main.core.Entity;
import main.core.Section;

public abstract class Air extends Entity {
    private String type;
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double toxicityAQ;
    private double airQuality;

    public Air(String name, double mass, Section section,
               String type, double humidity, double temperature,
               double oxygenLevel) {

        super(name, mass, section);
        this.type = type;
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }

    abstract public void calculateAirQuality();

    abstract public void calculateToxicityAQ();

    abstract public void updateAirQuality();

    public double getHumidity() {
        return this.humidity;
    }
    public void setHumidity(double humidity) {
        this.humidity = humidity;
        calculateAirQuality();
        calculateToxicityAQ();
    }

    public double getTemperature() {
        return this.temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
        calculateAirQuality();
        calculateToxicityAQ();
    }

    public double getOxygenLevel() {
        return this.oxygenLevel;
    }
    public void setOxygenLevel(double oxygenLevel) {
        this.oxygenLevel = oxygenLevel;
        calculateAirQuality();
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
