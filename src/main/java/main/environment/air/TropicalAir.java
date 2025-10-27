package main.environment.air;

public class TropicalAir extends Air {
    public static final double TOXICITY_MAX_SCORE = 82.0;

    private double co2Level;
    private double rainfallAmount = 0.0;

    public TropicalAir() {
        super();
        this.co2Level = 0.0;
        calculateQuality();
        calculateToxicityAQ();
    }

    public TropicalAir(String name, double mass, String type, double humidity,
                       double temperature, double oxygenLevel, double co2Level) {
        super(name, mass, type, humidity, temperature, oxygenLevel);

        this.co2Level = co2Level;

        calculateQuality();
        interpretQuality();
        calculateToxicityAQ();
    }

    @Override
    public double calculateQuality() {
        double airQuality = (getOxygenLevel() * 2) + (getHumidity() * 0.5) -
                (this.co2Level * 0.01);

        airQuality = Math.round(airQuality * 100.0) / 100.0;
        setAirQuality(airQuality);
        calculateToxicityAQ();
        return airQuality;
    }

    @Override
    public double updateQuality() {
        double newAirQuality = getAirQuality() + (this.rainfallAmount * 0.3);

        newAirQuality = Math.round(newAirQuality * 100.0) / 100.0;
        setAirQuality(newAirQuality);
        calculateToxicityAQ();
        return newAirQuality;
    }

    public double getCo2Level() {
        return this.co2Level;
    }

    public void setCo2Level(double co2Level) {
        this.co2Level = co2Level;
        calculateQuality();
        calculateToxicityAQ();
    }

    public double getRainfallAmount() {
        return this.rainfallAmount;
    }

    public void setRainfallAmount(double rainfallAmount) {
        this.rainfallAmount = rainfallAmount;
        calculateQuality();
        calculateToxicityAQ();
    }

}
