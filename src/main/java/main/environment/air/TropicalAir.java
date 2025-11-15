package main.environment.air;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fileio.CommandInput;
import main.core.Section;

public class TropicalAir extends Air {
    private double co2Level;
    @JsonIgnore
    private double rainfallAmount = 0.0;
    @JsonIgnore
    private int rainfallTimestamp = -1;

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

        setCo2Level(co2Level);

    }

    @Override
    public double calculateQuality() {
        double airQuality = (this.oxygenLevel * 2) + (this.humidity * 0.5) -
                (this.co2Level * 0.01);

        airQuality = Math.max(0, Math.min(100, airQuality));
        airQuality = Math.round(airQuality * 100.0) / 100.0;

        return airQuality;
    }

    @Override
    public double updateQuality() {
        double baseQuality = calculateQuality();
        double newAirQuality = baseQuality + (this.rainfallAmount * 0.3);

        newAirQuality = Math.max(0, Math.min(100, newAirQuality));
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
        if ("rainfall".equals(command.getType())) {
            this.rainfallAmount = command.getRainfall();
            this.rainfallTimestamp = command.getTimestamp();
            recalc();
            return true;
        }
        return false;
    }

    @Override
    public void interactWithEnvironment(Section section, int iteration) {
        super.interactWithEnvironment(section, iteration);

        if (this.rainfallAmount > 0 && iteration >= this.rainfallTimestamp + 2) {
            this.rainfallAmount = 0.0;
            this.rainfallTimestamp = -1;
            recalc();
        }
    }

    public double getCo2Level() {
        return this.co2Level;
    }

    public void setCo2Level(double co2Level) {
        this.co2Level = Math.max(0, Math.min(100, co2Level));
        this.co2Level = Math.round(this.co2Level * 100.0) / 100.0;
        recalc();
    }

    public double getRainfallAmount() {
        return this.rainfallAmount;
    }

    public void applyRainfall(double rainfallAmount) {
        this.rainfallAmount = rainfallAmount;
        recalc();
    }

}
