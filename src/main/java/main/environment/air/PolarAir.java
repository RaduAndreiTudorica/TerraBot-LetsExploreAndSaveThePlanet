package main.environment.air;

import main.core.Section;

public class PolarAir extends Air {

    private double iceCrystalConcentration;
    private double windSpeed = 0.0;

    public PolarAir() {
        super();
        this.iceCrystalConcentration = 0.0;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public PolarAir(String name, double mass, Section section, String type, double humidity,
                    double temperature, double oxygenLevel, double iceCrystalConcentration) {
        super(name, mass, section, type, humidity, temperature, oxygenLevel);

        this.iceCrystalConcentration = iceCrystalConcentration;

        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    @Override
    public double calculateQuality() {
        double airQuality = (this.oxygenLevel * 2) + (100 - Math.abs(getTemperature())) -
                (this.iceCrystalConcentration * 0.05);

        airQuality = Math.round(airQuality * 100.0) / 100.0;

        this.toxicityAQ = calculateToxicityAQ();
        return airQuality;
    }

    @Override
    public double updateQuality() {
        double newAirQuality = this.airQuality - (this.windSpeed * 0.02);

        newAirQuality = Math.round(newAirQuality * 100.0) / 100.0;

        this.toxicityAQ = calculateToxicityAQ();;
        return newAirQuality;
    }

    @Override
    double getMaxScore() {
        return 142.0;
    }

    public double getIceCrystalConcentration() {
        return this.iceCrystalConcentration;
    }

    public void setIceCrystalConcentration(double iceCrystalConcentration) {
        this.iceCrystalConcentration = iceCrystalConcentration;
        this.airQuality = calculateQuality();
        this.toxicityAQ = calculateToxicityAQ();
        interpretQuality();
    }

    public double getWindSpeed() {
        return this.windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
        this.airQuality = updateQuality();
        interpretQuality();
    }

}
