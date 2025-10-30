package main.environment.air;
import main.core.Section;

public class DesertAir extends Air{

    private double dustParticles;
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
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    @Override
    public double calculateQuality() {
        double airQuality = (this.oxygenLevel * 2) -
                (this.dustParticles * 0.2) - (this.temperature * 0.3);

        airQuality = Math.round(airQuality * 100.0) / 100.0;

        this.toxicityAQ = calculateToxicityAQ();
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

    public double getDustParticles() {
        return this.dustParticles;
    }

    public void setDustParticles(double dustParticles) {
        this.dustParticles = dustParticles;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public boolean isDesertStorm() {
        return this.isDesertStorm;
    }

    public void setDesertStorm(boolean isDesertStorm) {
        this.isDesertStorm = isDesertStorm;
        this.airQuality = updateQuality();
        interpretQuality();
    }
}
