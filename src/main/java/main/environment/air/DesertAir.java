package main.environment.air;

import main.core.Section;

public class DesertAir extends Air{
    public static final int TOXICITY_MAX_SCORE = 65;

    private double dustParticles;
    private String event = "desertStorm"

    public DesertAir(String name, double mass, Section section, String type, double humidity,
                     double temperature, double oxygenLevel, double dustParticles) {
        super(name, mass, section, type, humidity, temperature, oxygenLevel);

        this.dustParticles = dustParticles;
        calculateAirQuality();
        calculateToxicityAQ();
    }

    @Override
    public void calculateAirQuality() {
        double airQuality = (getOxygenLevel() * 2) -
                (this.dustParticles * 0.2) - (getTemperature() * 0.3);

        airQuality = Math.round(airQuality * 100.0) / 100.0;

        setAirQuality(airQuality);
    }

    @Override
    public void calculateToxicityAQ() {
        double toxicityAQ = 100 * (1 - getAirQuality() / TOXICITY_MAX_SCORE);

        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0;

        setToxicityAQ(toxicityAQ);
    }

    public double getDustParticles() {
        return this.dustParticles;
    }

    public void setDustParticles(double dustParticles) {
        this.dustParticles = dustParticles;
        calculateAirQuality();
        calculateToxicityAQ();
    }
}
