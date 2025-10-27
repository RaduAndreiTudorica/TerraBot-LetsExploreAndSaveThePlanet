package main.environment.air;

public class DesertAir extends Air{
    public static final double TOXICITY_MAX_SCORE = 65.0;

    private double dustParticles;
    private boolean isDesertStorm = false;

    public DesertAir() {
        super();
        this.dustParticles = 0.0;
        calculateQuality();
        calculateToxicityAQ();
    }

    public DesertAir(String name, double mass, String type, double humidity,
                     double temperature, double oxygenLevel, double dustParticles) {
        super(name, mass, type, humidity, temperature, oxygenLevel);

        this.dustParticles = dustParticles;
        calculateQuality();
        interpretQuality();
        calculateToxicityAQ();
    }

    @Override
    public double calculateQuality() {
        double airQuality = (getOxygenLevel() * 2) -
                (this.dustParticles * 0.2) - (getTemperature() * 0.3);

        airQuality = Math.round(airQuality * 100.0) / 100.0;

        setAirQuality(airQuality);
        calculateToxicityAQ();
        return airQuality;
    }

    @Override
    public double updateQuality() {
        double newAirQuality = getAirQuality() - (this.isDesertStorm ? 30 : 0);

        newAirQuality = Math.round(newAirQuality * 100.0) / 100.0;
        setAirQuality(newAirQuality);
        calculateToxicityAQ();
        return newAirQuality;
    }

    public double getDustParticles() {
        return this.dustParticles;
    }

    public void setDustParticles(double dustParticles) {
        this.dustParticles = dustParticles;
        calculateQuality();
        calculateToxicityAQ();
    }

    public boolean isDesertStorm() {
        return this.isDesertStorm;
    }

    public void setDesertStorm(boolean isDesertStorm) {
        this.isDesertStorm = isDesertStorm;
        updateQuality();
    }
}
