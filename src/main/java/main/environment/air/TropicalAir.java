package main.environment.air;

import fileio.CommandInput;
import main.core.Section;

public class TropicalAir extends Air {
    private double co2Level;
    private double rainfallAmount = 0.0;

    public TropicalAir() {
        super();
        this.co2Level = 0.0;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public TropicalAir(String name, double mass, Section section, String type, double humidity,
                        double temperature, double oxygenLevel, double co2Level) {
        super(name, mass, section, type, humidity, temperature, oxygenLevel);

        this.co2Level = co2Level;

        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    @Override
    public double calculateQuality() {
        double airQuality = (this.oxygenLevel * 2) + (this.humidity * 0.5) -
                (this.co2Level * 0.01);

        airQuality = Math.max(0, Math.min(100, airQuality));
        airQuality = Math.round(airQuality * 100.0) / 100.0;


        this.toxicityAQ = calculateToxicityAQ();
        return airQuality;
    }

    @Override
    public double updateQuality() {
        double newAirQuality = this.airQuality + (this.rainfallAmount * 0.3);

        newAirQuality = Math.round(newAirQuality * 100.0) / 100.0;

        this.toxicityAQ = calculateToxicityAQ();
        return newAirQuality;
    }

    @Override
    double getMaxScore() {
        return 82.0;
    }

    @Override
    public boolean applyWeatherEvent(CommandInput command) {
        if ("rainfall".equals(command.type)) {
            this.rainfallAmount = command.rainfall;
            return true;
        }
        return false;
    }

    public double getCo2Level() {
        return this.co2Level;
    }

    public void setCo2Level(double co2Level) {
        this.co2Level = co2Level;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public double getRainfallAmount() {
        return this.rainfallAmount;
    }

    public void applyRainfall(double rainfallAmount) {
        this.rainfallAmount = rainfallAmount;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

}
