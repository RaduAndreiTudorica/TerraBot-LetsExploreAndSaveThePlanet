package main.environment.air;

public class PolarAir extends Air {
    public static final double TOXICITY_MAX_SCORE = 142.0;

    private double iceCrystalConcentration;
    private double windSpeed = 0.0;

    public PolarAir() {
        super();
        this.iceCrystalConcentration = 0.0;
        calculateQuality();
        calculateToxicityAQ();
    }

    public PolarAir(String name, double mass, String type, double humidity,
                    double temperature, double oxygenLevel, double iceCrystalConcentration) {
        super(name, mass, type, humidity, temperature, oxygenLevel);

        this.iceCrystalConcentration = iceCrystalConcentration;

        calculateQuality();
        interpretQuality();
        calculateToxicityAQ();
    }

    @Override
    public double calculateQuality() {
        double airQuality = (getOxygenLevel() * 2) + (100 - Math.abs(getTemperature())) -
                (this.iceCrystalConcentration * 0.05);

        airQuality = Math.round(airQuality * 100.0) / 100.0;
        setAirQuality(airQuality);
        calculateToxicityAQ();
        return airQuality;
    }

    @Override
    public double updateQuality() {
        double newAirQuality = getAirQuality() - (this.windSpeed * 0.02);

        newAirQuality = Math.round(newAirQuality * 100.0) / 100.0;
        setAirQuality(newAirQuality);
        calculateToxicityAQ();
        return newAirQuality;
    }

    public double getIceCrystalConcentration() {
        return this.iceCrystalConcentration;
    }

    public void setIceCrystalConcentration(double iceCrystalConcentration) {
        this.iceCrystalConcentration = iceCrystalConcentration;
        calculateQuality();
        calculateToxicityAQ();
    }

    public double getWindSpeed() {
        return this.windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
        updateQuality();
    }

}
