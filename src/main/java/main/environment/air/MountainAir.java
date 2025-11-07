package main.environment.air;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CommandInput;
import main.core.Section;

public class MountainAir extends Air {
    private double altitude;
    @JsonIgnore
    private int numberOfHikers = 0;

    public MountainAir() {
        super();
        this.altitude = 0.0;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public MountainAir(String name, double mass, Section section, String type, double humidity,
                        double temperature, double oxygenLevel, double altitude) {
        super(name, mass, section, type, humidity, temperature, oxygenLevel);

        this.altitude = altitude;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    @Override
    public double calculateQuality() {
        double oxygenFactor = this.oxygenLevel - (this.altitude / 1000.0 * 0.5);
        double airQuality = (oxygenFactor * 2) + (this.humidity * 0.6);

        airQuality = Math.max(0, Math.min(100, airQuality));
        airQuality = Math.round(airQuality * 100.0) / 100.0;

        return airQuality;
    }

    @Override
    public double updateQuality() {
        double newAirQuality = this.airQuality - (this.numberOfHikers * 0.1);

        newAirQuality = Math.round(newAirQuality * 100.0) / 100.0;

        this.toxicityAQ = calculateToxicityAQ();
        return newAirQuality;
    }

    @Override
    public double getMaxScore() {
        return 78.0;
    }

    @Override
    public boolean applyWeatherEvent(CommandInput command) {
        if ("peopleHiking".equals(command.getType())) {
            this.numberOfHikers = command.getNumberOfHikers();
            return true;
        }
        return false;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }
}
