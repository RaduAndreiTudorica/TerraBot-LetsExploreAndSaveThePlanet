package main.environment.air;

import com.fasterxml.jackson.annotation.JsonIgnore;
import main.core.Section;
import fileio.CommandInput;

public class DesertAir extends Air{
    @JsonIgnore
    private double dustParticles;
    @JsonIgnore
    private boolean isDesertStorm = false;

    public DesertAir() {
        super();
        this.dustParticles = 0.0;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public DesertAir(String name, double mass, Section section, String type, double humidity,
                        double temperature, double oxygenLevel, double dustParticles) {
        super(name, mass, section, type, humidity, temperature, oxygenLevel);

        this.dustParticles = dustParticles;
        recalc();
    }

    @Override
    public double calculateQuality() {
        double airQuality = (this.oxygenLevel * 2) -
                (this.dustParticles * 0.2) - (this.temperature * 0.3);

        airQuality = Math.max(0, Math.min(100, airQuality));
        airQuality = Math.round(airQuality * 100.0) / 100.0;

        return airQuality;
    }

    @Override
    public double updateQuality() {
        double newAirQuality = this.airQuality - (this.isDesertStorm ? 30 : 0);

        newAirQuality = Math.round(newAirQuality * 100.0) / 100.0;

        this.toxicityAQ = calculateToxicityAQ();
        return newAirQuality;
    }

    @Override
    public double getMaxScore() {
        return 65.0;
    }

    @Override
    public boolean applyWeatherEvent(CommandInput command) {
        if ("desertStorm".equals(command.getType())) {
            this.isDesertStorm = true;
            return true;
        }
        return false;
    }

    public double getDustParticles() {
        return this.dustParticles;
    }

    public void setDustParticles(double dustParticles) {
        this.dustParticles = dustParticles;
        recalc();
    }

    public boolean isDesertStorm() {
        return this.isDesertStorm;
    }

    public void setDesertStorm(boolean isDesertStorm) {
        this.isDesertStorm = isDesertStorm;
        this.airQuality = updateQuality();
        interpretQuality();
    }

    public boolean getIsDesertStorm() {
        return isDesertStorm;
    }
}
